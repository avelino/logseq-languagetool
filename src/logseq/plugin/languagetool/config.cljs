(ns languagetool.config
  (:require ["@logseq/libs"]))

(def default-languagetool-host "https://api.languagetoolplus.com/v2")
(defn get-setting
  "Get `setting-key` from Logseq settings"
  [setting-key]
  (get (js->clj js/logseq.settings)
       (str (name setting-key))
       (if (= setting-key :languagetool-host) default-languagetool-host "")))

(defn schema
  "Schema for the Logseq settings"
  [langs]
  [{:key "languagetool-enable",
    :type "boolean",
    :title "LanguageTool Enabled?",
    :description "You can deactivate languageetool, it is ON by default",
    :default true}
   {:key "languagetool-username",
    :type "string",
    :title "LanguageTool Username",
    :description "Please use the Username from LanguageTool's API console",
    :default ""}
   {:key "languagetool-apikey",
    :type "string",
    :title "LanguageTool API KEY",
    :description "Please use the API KEY from LanguageTool's API console",
    :default ""}
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
