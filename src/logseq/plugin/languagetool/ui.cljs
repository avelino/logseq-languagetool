(ns languagetool.ui)

(def trigger-icon-name "ti-bell")

(js/logseq.App.registerUIItem
 "toolbar"
 (clj->js
  {:key "open-languagetool"
   :template (str "<a class=\"button\" data-on-click=\"controlGrammarly\" data-rect>"
                  "<i class=\"ti " trigger-icon-name "\"></i>"
                  "</a>")}))

(def languagetool-enable (get (js->clj js/logseq.settings) "languagetool-enable"))
(def enable-color "#2cb673")
(def disable-color "#6b7280")
(def background-color (if languagetool-enable
                        enable-color
                        disable-color))

(js/logseq.provideStyle (str "." trigger-icon-name
                             ":before { color: "
                             background-color "; }"))

(defn control-languagetool
  "Control LanguageTool Style"
  [e]
  (if languagetool-enable
    (js/logseq.provideStyle (str "." trigger-icon-name
                                 ":before { color: "
                                 enable-color "; }"))
    (js/logseq.provideStyle (str "." trigger-icon-name
                                 ":before { color: "
                                 disable-color "; }"))))

(defn create-model [] {:controlLanguageTool control-languagetool})
