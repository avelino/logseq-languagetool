(ns languagetool.block)

(defn set-item!
  "Set `key' in browser's localStorage to `val`."
  [key val]
  (.setItem (.-localStorage js/window) key val))

(defn get-item
  "Returns value of `key' from browser's localStorage."
  [key]
  (.getItem (.-localStorage js/window) key))

(defn remove-item!
  "Remove the browser's localStorage value for the given `key`"
  [key]
  (.removeItem (.-localStorage js/window) key))

(defn control
  "control of the block/text that has been validated"
  [uuid text]
  (let [key (keyword uuid)
        hash-old (get-item key)
        hash-text (str (hash text))]
    (if (= hash-text hash-old)
      false
      (do
        (remove-item! key)
        (set-item! key hash-text)
        true))))
