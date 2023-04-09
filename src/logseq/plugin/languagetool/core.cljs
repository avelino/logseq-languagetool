(ns languagetool.core
  (:require [languagetool.ui :as ui]
            [languagetool.config :as config]
            [languagetool.api :as api]
            [promesa.core :as p]))

(def mutation-observer (.-MutationObserver js/parent))

(defn throttle
  "throttle MutationObserver"
  ;; from https://stackoverflow.com/a/52868150"
  [func limit]
  (let [inThrottle (atom false)]
    (fn [& args]
      (if (not @inThrottle)
        (do
          (apply func args)
          (reset! inThrottle true)
          (js/setTimeout #(reset! inThrottle false) limit))))))

(defn callback
  [mutationList observer]
  (for [mutation mutationList]
    ;; Attaching editor plugin textarea in editing block.
    ;; They'll be grammarly-textarea after attaching.
    (when (and (= (.type mutation) "attributes")
               (= (.nodeName (.target mutation)) "TEXTAREA")
               (= (.ariaLabel (.target mutation)) "editing block")
               (not= (.nodeName (.parentNode (.target mutation))) "LANGUAGETOOL-EDITOR-PLUGIN"))
      (let [target (.target mutation)]
        (js/console.log "Attaching LanguageTool Editor to" target)
        (when (not= (.nodeName (.parentNode target)) "LANGUAGETOOL-EDITOR-PLUGIN") next)
        (.classList (.parentNode target) "add" "ignore-outside-event")))))


(defn main []
  (def watchTarget
    (.getElementById (.-document js/parent) "main-content-container"))
  (def observer (new mutation-observer (throttle callback 200)))
  (.observe observer watchTarget #js {:attributes true
                                      :subtree true})

  (js/logseq.provideStyle ".cp__sidebar-help-btn { bottom: 1rem; position: fixed; right: 5rem;}")
  (js/logseq.provideModel ui/create-model)
  (js/logseq.App.showMsg "Hello from LanguageTool plugin in CLJS!"))

(defn -init []
  (-> (js/logseq.useSettingsSchema (clj->js config/schema))
      (.ready main)
      (.catch js/console.error)))
