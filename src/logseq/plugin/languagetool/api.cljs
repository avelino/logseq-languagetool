(ns languagetool.api
  "Communication package with the LanguageTool API
   https://languagetoolplus.com/http-api/"
  (:require [clojure.walk :as walk]
            [languagetool.config :refer [get-setting]]
            [lambdaisland.fetch :as fetch]
            [promesa.core :as p]))

(defn check
  "Check a text using languagetool plus API"
  [text & {:keys [lang enabledOnly]
           :or {lang "auto" enabledOnly false}}]
  (p/let [url (str (get-setting :languagetool-host) "/check")
          params {:content-type :form-encoded
                  :accept :json
                  :body {:text text
                         :language lang
                         ;; "top.ice8700@avelino.run"
                         :username (get-setting :languagetool-username)
                         ;; "pit-RgGgjuiZILhc"
                         :apiKey (get-setting :languagetool-apikey)
                         :enabledOnly enabledOnly}}]
    (-> (fetch/post url params)
        (.then #(p/resolved (:body %))))))

(defn languages
  "Get a list of supported languages"
  []
  (p/let [resp (fetch/get (str (get-setting :languagetool-host) "/languages"))]
    (walk/keywordize-keys (js->clj (:body resp)))))
