(ns authpass.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page :only [include-css html5]]))

(defpartial layout [& content]
            (html5
              [:head
               [:title "authpass"]
               (include-css "/css/reset.css")]
              [:body
               [:div#wrapper
                content]]))
