(ns languagetool.api
  "Communication package with the LanguageTool API
   https://languagetool.org/http-api/"
  (:require [lambdaisland.fetch :as fetch]
            [promesa.core :as p]
            [clojure.walk :as walk]
            [logseq-libs.settings :as setting]))

(def languagetool-host
  (or (get (js->clj setting/settings) "languagetool-host")
      "https://api.languagetool.org/"))

(defn check
  "Check a text"
  ;; curl -d "text=This is an test." -d "language=auto" https://api.languagetool.org/v2/check
  [text & {:keys [lang] :or {lang "auto"}}]
  (p/let [resp (fetch/get
                (str languagetool-host "v2/check")
                {:query-params {"text" text
                                "language" lang
                                "enabledOnly" false}})
          body (walk/keywordize-keys (js->clj (:body resp)))]
    body))

(defn languages
  "Get a list of supported languages"
  []
  (p/let [resp (fetch/get (str languagetool-host "v2/languages"))
          body (walk/keywordize-keys (js->clj (:body resp)))]
    body))
