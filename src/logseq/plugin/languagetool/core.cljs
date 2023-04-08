(ns languagetool.core
  (:require [languagetool.ui :as ui]
            [languagetool.api :as api]
            [promesa.core :as p]))

(defn main []
  (js/logseq.provideStyle ".cp__sidebar-help-btn { bottom: 1rem; position: fixed; right: 5rem;}")
  (js/logseq.provideModel ui/create-model)
  (js/logseq.App.showMsg "Hello from LanguageTool plugin in CLJS!"))

(defn init []
  (-> (js/logseq.ready main)
      (.catch js/console.error)))

(def default-languagetool-clientid "abc")
(def default-languagetool-host "https://languagetool.org/api/")
(def languagetool-languages ["ar" "ast-ES" "be-BY" "br-FR" "ca-ES" "ca-ES-valencia" "ca-ES-balear" "da-DK" "de" "de-DE" "de-AT" "de-CH" "de-DE-x-simple-language" "el-GR" "en" "en-US" "en-GB" "en-AU" "en-CA" "en-NZ" "en-ZA" "eo" "es" "es-AR" "fa" "fr" "ga-IE" "gl-ES" "it" "ja-JP" "km-KH" "nl" "nl-BE" "pl-PL" "pt" "pt-PT" "pt-BR" "pt-AO" "pt-MZ" "ro-RO" "ru-RU" "sk-SK" "sl-SI" "sv" "ta-IN" "tl-PH" "uk-UA" "zh-CN" "fr" "en" "en-US" "en-AU" "en-GB" "en-CA" "en-NZ" "en-ZA" "es" "pt-AO" "pt-BR" "pt-MZ" "pt-PT" "nl" "de" "de-DE" "de-AT" "de-CH" "nb" "no" "nl-NL" "de-DE-x-simple-language-DE" "es-ES" "it-IT" "fa-IR" "sv-SE" "de-LU" "fr-FR"])
(def settingsSchema
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
      :enumChoices languagetool-languages,
      :enumPicker "select",
      :title "Language Support",
      :description "Select the main language",
      :default "en-US"}])

(-> (js/logseq.useSettingsSchema (clj->js settingsSchema))
    (.ready main)
    (.catch js/console.error))
