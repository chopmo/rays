(ns rays.projectiles
  (:require [rays.tuples :as t]
            [rays.canvas :as c]
            [rays.colors :as col]
            [rays.ppm :as ppm]))

(defn ->projectile [position velocity]
  {:position position
   :velocity velocity})

(defn ->environment [gravity wind]
  {:gravity gravity
   :wind wind})

(defn tick [env proj]
  (let [pos (-> (:position proj)
                (t/add (:velocity proj)))
        vel (-> (:velocity proj)
                (t/add (:gravity env))
                (t/add (:wind env)))]
    (->projectile pos vel)))


(comment
  (let [p (->projectile (t/->point 0 1 0)
                        (-> (t/->vect 1 1 0)
                            t/normalize
                            (t/multiply 2)))
        e (->environment (t/->vect 0 -0.1 0)
                         (t/->vect -0.01 0 0))]

    ;; Shoot a projectile p with environment e and report the number of
    ;; ticks it takes before it hits the ground.
    (->> (iterate (partial tick e) p)
         (map vector (range))
         (filter (fn [[_ proj]] (<= (-> proj :position :y) 0)))
         ffirst))
  )

(comment
  (let [env    (->environment (t/->vect 0 -0.1 0)
                              (t/->vect -0.01 0 0))
        height 400
        width  600]
    (->> (loop [p
               (->projectile (t/->point 0 1 0)
                             (-> (t/->vect 1 2 0)
                                 t/normalize
                                 (t/multiply 8)))

               canvas (c/->canvas width height)]
          (if (neg? (-> p :position :y))
            canvas
            (recur (tick env p)
                   (c/write-pixel canvas
                                  (-> p :position :x int)
                                  (- height (-> p :position :y int))
                                  (col/->color 1 1 1)))))
         ppm/canvas-to-ppm
         (spit "foo.ppm")
         ))
)
