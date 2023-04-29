(ns languagetool.logseq
  (:require ["@logseq/libs"]))

(def get-current-block js/logseq.Editor.getCurrentBlock)
(def get-editing-block-content js/logseq.Editor.getEditingBlockContent)
(def update-block js/logseq.Editor.updateBlock)

;; ui
;; TODO: it's not working
(def provide-style js/logseq.provideStyle)
(def provide-model js/logseq.provideModel)

;; app
(def app-show-msg js/logseq.App.showMsg)
(def app-register-ui-item js/logseq.App.registerUIItem)

;; settings
(def settings js/logseq.settings)
;; TODO: it's not working
(def use-setting-schema js/logseq.useSettingsSchema)
