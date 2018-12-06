(ns rays.matrix)

(defn ->mat4 [& xs]
  (vec (map vec (partition 4 xs))))

(defn at [m r c]
  (get-in m [r c]))
