(ns languagetool.api
  "Communication package with the LanguageTool API
   https://languagetoolplus.com/http-api/"
  (:require [clojure.walk :as walk]
            [lambdaisland.fetch :as fetch]
            [logseq-libs.settings :as setting]
            [promesa.core :as p]))

(def languagetool-host
  (or (get (js->clj setting/settings) "languagetool-host")
      "https://api.languagetoolplus.com/v2"))

(defn check
  "Check a text using languagetool plus API"
  [text & {:keys [lang enabledOnly]
           :or {lang "auto" enabledOnly false}}]
  (p/let [url (str languagetool-host "/check")
          params {:content-type :form-encoded
                  :accept :json
                  :body {:text text
                         :language lang
                         :username "top.ice8700@avelino.run"
                         :apiKey "pit-RgGgjuiZILhc"
                         :enabledOnly enabledOnly}}]
    (-> (fetch/post url params)
        (.then #(p/resolved (:body %))))))

(defn languages
  "Get a list of supported languages"
  []
  (p/let [resp (fetch/get (str languagetool-host "/languages"))]
    (walk/keywordize-keys (js->clj (:body resp)))))
