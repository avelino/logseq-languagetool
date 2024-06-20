(ns languagetool.config
  (:require [languagetool.api :as api]
            [promesa.core :as p]))

(def default-languagetool-clientid "abc")
(def default-languagetool-host "https://api.languagetoolplus.com/v2")

(defn languagetool-languages-dynamic []
  "Get a list of supported languages"
  (p/let [langs (api/languages)
          ret (mapv :longCode langs)]
    (clj->js (merge ["auto"] ret))))

(defn schema
  "Schema for the Logseq settings"
  [langs]
  [{:key "languagetool-enable",
    :type "boolean",
    :title "LanguageTool Enabled?",
    :description "You can deactivate languageetool, it is ON by default",
    :default true}
   {:key "languagetool-clientid",
    :type "string",
    :title "LanguageTool Client ID",
    :description "Please use the Client ID from LanguageTool's API console",
    :default default-languagetool-clientid}
   {:key "languagetool-host",
    :type "string",
    :title "LanguageTool Host Server",
    :description "Set the address of the languagetool server",
    :default default-languagetool-host}
   {:key "languagetool-languages",
    :type "enum",
    :enumChoices langs,
    :enumPicker "select",
    :title "Language Support",
    :description "Select the main language",
    :default "auto"}])
    
