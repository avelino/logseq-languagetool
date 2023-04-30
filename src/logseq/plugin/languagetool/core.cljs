(ns languagetool.core
  (:require ["@logseq/libs"]
            [languagetool.ui :as ui]
            [languagetool.config :as config]
            [languagetool.observer :as ob]
            [languagetool.api :as api]
            [logseq-libs.editor :as editor]
            [logseq-libs.app :as app]
            [promesa.core :as p]))

(defn callback
  "process block change event"
  [mutations observer]
  (p/let [current-block (editor/get-current-block)
          block-uuid (aget current-block "uuid")
          block-content (editor/get-editing-block-content)]
    (when (not-empty block-content)
      (p/let [api-ret (api/check block-content)]
        (prn :api-check api-ret)
        (prn :content block-content :uuid block-uuid)))))

(defn main []
  (.observe (ob/load callback)
            ob/watch-target #js {:attributes true
                                 :subtree true})

  (js/logseq.provideStyle ".cp__sidebar-help-btn { bottom: 1rem; position: fixed; right: 5rem;}")
  (js/logseq.provideModel ui/create-model)
  (app/show-msg "Hello from LanguageTool plugin in CLJS!"))

(defn -init []
  (-> (js/logseq.useSettingsSchema (clj->js config/schema))
      (.ready main)
      (.catch js/console.error)))
