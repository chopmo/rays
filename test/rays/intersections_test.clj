(ns rays.intersections-test
  (:require [rays.intersections :as sut]
            [rays.spheres :as s]
            [clojure.test :refer :all]
            [rays.common :as c]
            [rays.rays :as r]
            [rays.tuples :as t]))


(deftest intersections-test
  (testing "an intersection encapsulates t and object"
    (let [s (s/sphere)
          i (sut/intersection 3.5 s)]
      (is (c/eq-floats? 3.5 (sut/t i)))
      (is (= s (sut/object i)))))

  (testing "aggregating intersections"
    (let [s (s/sphere)
          i1 (sut/intersection 1 s)
          i2 (sut/intersection 2 s)
          xs (sut/intersections i1 i2)]
      (is (= 2 (count xs)))
      (is (= 1 (sut/t (first xs))))
      (is (= 2 (sut/t (second xs))))))

  (testing "intersect sets the object on the intersection"
    (let [r (r/ray (t/->point 0 0 -5) (t/->vect 0 0 1))
          s (s/sphere)
          xs (s/intersect s r)]
      (is (= 2 (count xs)))
      (is (= s (sut/object (first xs))))
      (is (= s (sut/object (second xs)))))))
