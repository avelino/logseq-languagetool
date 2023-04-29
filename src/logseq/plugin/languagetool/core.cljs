(ns languagetool.core
  (:require [languagetool.logseq :as logseq]
            [languagetool.ui :as ui]
            [languagetool.config :as config]
            [languagetool.observer :as ob]
            [languagetool.api :as api]
            [promesa.core :as p]))

(defn callback
  "process block change event"
  [mutations observer]
  (p/let [current-block (logseq/get-current-block)
          block-uuid (aget current-block "uuid")
          block-content (logseq/get-editing-block-content)]
    (when (not-empty block-content)
      (prn :content block-content :uuid block-uuid))))

(defn main []
  (.observe (ob/load callback)
            ob/watch-target #js {:attributes true
                                 :subtree true})

  (js/logseq.provideStyle ".cp__sidebar-help-btn { bottom: 1rem; position: fixed; right: 5rem;}")
  (js/logseq.provideModel ui/create-model)
  (logseq/app-show-msg "Hello from LanguageTool plugin in CLJS!"))

(defn -init []
  (-> (js/logseq.useSettingsSchema (clj->js config/schema))
      (.ready main)
      (.catch js/console.error)))
