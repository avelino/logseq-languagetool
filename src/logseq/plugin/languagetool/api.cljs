(ns languagetool.api
  "Communication package with the LanguageTool API
   https://languagetool.org/http-api/"
  (:require [lambdaisland.fetch :as fetch]
            [promesa.core :as p]
            [clojure.walk :as walk]
            [languagetool.logseq :as logseq]))

(def languagetool-host (get (js->clj logseq/settings) "languagetool-host"))

(defn check
  "Check a text"
  [lang text]
  (p/let [resp (fetch/get
                (str languagetool-host "v2/check")
                {:query-params {"text" text
                                "language" lang
                                "enabledOnly" false}})]
    (walk/keywordize-keys (js->clj (:body resp)))))

(defn languages
  "Get a list of supported languages"
  []
  (p/let [resp (fetch/get (str languagetool-host "v2/languages"))]
    (walk/keywordize-keys (js->clj (:body resp)))))
