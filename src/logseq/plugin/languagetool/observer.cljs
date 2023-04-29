(ns languagetool.observer)

(def mutation-observer (.-MutationObserver js/parent))

(defn throttle
  "throttle MutationObserver"
  ;; from https://stackoverflow.com/a/52868150"
  [func limit]
  (let [inThrottle (atom false)]
    (fn [& args]
      (if (not @inThrottle)
        (do
          (apply func args)
          (reset! inThrottle true)
          (js/setTimeout #(reset! inThrottle false) limit))))))

(def watch-target
  (.getElementById (.-document js/parent) "main-content-container"))

(defn load
  "observer to watch for changes in DOM"
  [callback & {:keys [timeout] :or {timeout 200}}]
  (new mutation-observer (throttle callback timeout)))
