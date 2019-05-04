(ns rays.intersections)


(defn intersection [t object]
  {:t t
   :object object})

(defn intersections [& is]
  is)

(defn t [i]
  (:t i))

(defn object [i]
  (:object i))

(defn hit [xs]
  (->> xs
       (sort-by :t)
       (filter #(pos? (:t %)))
       first))
