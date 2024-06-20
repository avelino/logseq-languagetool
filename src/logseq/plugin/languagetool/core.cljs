(ns languagetool.core
  (:require ["@logseq/libs"]
            [languagetool.api :as api]
            [languagetool.config :as config]
            [logseq-libs.app :as app]
            [promesa.core :as p]))

(defn main []
  (p/let [translate (api/check "Olá, tudo bem? Como voce esta, iso" "pt-BR")]
    (println :translate translate)
    (app/show-msg (str "Translation: " translate))))

(defn -init []
  (p/let [langs (config/languagetool-languages-dynamic)]
    (-> (js/logseq.useSettingsSchema
         (clj->js (config/schema langs)))
        (.ready main)
        (.catch js/console.error))))
