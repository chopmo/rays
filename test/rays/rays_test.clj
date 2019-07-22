(ns rays.rays-test
  (:require  [clojure.test :refer :all]
             [rays.rays :as sut]
             [rays.tuples :as t]
             [rays.matrix :as m]))


(deftest test-origin-and-direction
  (let [origin    (t/->point 1 2 3)
        direction (t/->vect 4 5 6)
        r         (sut/ray origin direction)]
    (is (= origin (sut/origin r)))
    (is (= direction (sut/direction r)))))

(deftest test-position
  (let [r (sut/ray (t/->point 2 3 4)
                   (t/->vect 1 0 0))]
    (is (t/equal? (t/->point 2 3 4) (sut/position r 0)))
    (is (t/equal? (t/->point 3 3 4) (sut/position r 1)))
    (is (t/equal? (t/->point 1 3 4) (sut/position r -1)))
    (is (t/equal? (t/->point 4.5 3 4) (sut/position r 2.5)))))

(deftest test-translation
  (let [r  (sut/ray (t/->point 1 2 3)
                    (t/->vect 0 1 0))
        m  (m/translation 3 4 5)
        r2 (sut/transform r m)]
    (is (= (t/->point 4 6 8)
           (sut/origin r2)))
    (is (= (t/->vect 0 1 0)
           (sut/direction r2)))))

(deftest test-scaling
  (let [r  (sut/ray (t/->point 1 2 3)
                    (t/->vect 0 1 0))
        m  (m/scaling 2 3 4)
        r2 (sut/transform r m)]
    (is (= (t/->point 2 6 12)
           (sut/origin r2)))
    (is (= (t/->vect 0 3 0)
           (sut/direction r2)))))
