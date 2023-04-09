(ns languagetool.core
  (:require [languagetool.ui :as ui]
            [languagetool.config :as config]
            [languagetool.api :as api]
            [promesa.core :as p]))

(defn main []
  (js/logseq.provideStyle ".cp__sidebar-help-btn { bottom: 1rem; position: fixed; right: 5rem;}")
  (js/logseq.provideModel ui/create-model)
  (js/logseq.App.showMsg "Hello from LanguageTool plugin in CLJS!"))

(defn -init []
  (-> (js/logseq.useSettingsSchema (clj->js config/schema))
      (.ready main)
      (.catch js/console.error)))
