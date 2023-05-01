(ns languagetool.ui
  (:require ["@logseq/libs"]))

(def trigger-icon-name "ti-bell")
(def languagetool-enable (or (get (js->clj js/logseq.settings) "languagetool-enable")
                             true))
(def enable-color "#2cb673")
(def disable-color "#6b7280")
(def background-color (if languagetool-enable
                        enable-color
                        disable-color))

(def dsl #js {:key "open-languagetool"
              :close "outside",
              :attrs {:title "LanguageTool"}
              :template (str "<a class='button' data-on-click='control-languagetool' data-rect>"
                             "<i class='ti " trigger-icon-name "'></i>"
                             "</a>")})

(js/logseq.App.registerUIItem
 "toolbar"
 dsl)

(js/logseq.provideUI dsl)

(js/logseq.provideStyle (str "." trigger-icon-name
                             ":before { color: "
                             background-color "; }"))

(defn control-languagetool
  "Control LanguageTool Style"
  []
  (if languagetool-enable
    (js/logseq.provideStyle (str "." trigger-icon-name ":before { color: " enable-color "; }"))
    (js/logseq.provideStyle (str "." trigger-icon-name ":before { color: " disable-color "; }"))))

(defn create-model []
  #js {:control-languagetool (control-languagetool)})
