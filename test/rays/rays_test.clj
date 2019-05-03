(ns rays.rays-test
  (:require  [clojure.test :refer :all]
             [rays.rays :as sut]
             [rays.tuples :as t]))


(deftest test-rays
  (testing "creating and querying a ray"
    (let [origin    (t/->point 1 2 3)
          direction (t/->vect 4 5 6)
          r         (sut/ray origin direction)]
      (is (= origin (sut/origin r)))
      (is (= direction (sut/direction r)))))

  (testing "computing a point from a distance"
    (let [r (sut/ray (t/->point 2 3 4)
                     (t/->vect 1 0 0))]
      (is (t/equal? (t/->point 2 3 4) (sut/position r 0)))
      (is (t/equal? (t/->point 3 3 4) (sut/position r 1)))
      (is (t/equal? (t/->point 1 3 4) (sut/position r -1)))
      (is (t/equal? (t/->point 4.5 3 4) (sut/position r 2.5))))))
