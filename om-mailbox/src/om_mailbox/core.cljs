(ns ^:figwheel-always om-mailbox.core
    (:require [om.core :as om :include-macros true]
              [om.dom :as dom :include-macros true]))

;;; Components

(defn email-view [data owner]
  (reify
    om/IRender
    (render [this]
      (dom/div #js {:className "email"}
        (dom/dl #js {:className "meta dl-horizontal"}
          (dom/dt nil "From")
          (dom/dl nil (:from data))

          (dom/dt nil "To")
          (dom/dl nil (:to data))

          (dom/dt nil "Subject")
          (dom/dl nil (:subject data)))
        (dom/div #js {:className "body"
                      :dangerouslySetInnerHTML {:__html (:body data)}})))))

(defn email-list-item-view [data owner]
  (reify
    om/IRender
    (render [this]
      (dom/tr #js {:onClick (fn [e] ((:on-click data)))}
        (dom/td nil (:subject data))
        (dom/td nil (:from data))
        (dom/td nil (:to data))))))

(defn email-list-view [data owner]
  (reify
    om/IRender
    (render [this]
      (dom/table #js {:className "email-list table table-striped table-condensed"}
        (dom/thead nil
          (dom/tr nil
            (dom/th nil "Subject")
            (dom/th nil "From")
            (dom/th nil "To")))
        (dom/tbody nil
          (om/build-all email-list-item-view (:emails data)))))))


;;; Applicaion fixtures

(defonce app-state
  (atom {:mailboxes
         [
          {:id 1
           :name "Inbox"
           :emails
           [
            {:id 1
             :from "joe@tryolabs.com"
             :to "fernando@tryolabs.com"
             :subject "Meeting"
             :body "..."
            }
            {:id 2
             :from "newsbot@tryolabs.com"
             :to "fernando@tryolabs.com"
             :subject "News Digest"
             :body "<h1>Intro to React</h1> <img src='https://raw.githubusercontent.com/wiki/facebook/react/react-logo-1000-transparent.png' width=300/)>"
            }
           ]
          }
          {:id 2
           :name "Spam"
           :emails
           [
            {:id 3
             :from "nigerian.prince@gmail.com"
             :to "fernando@tryolabs.com"
             :subject "Obivous 419 scam"
             :body "You've won the prize!!!1!1!!!"
            }
           ]
          }
         ]
        }))

(om/root
  (fn [data owner]
    (reify om/IRender
      (render [_]
        (dom/h1 nil (:text data)))))
  app-state
  {:target (. js/document (getElementById "app"))})