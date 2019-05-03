(ns rays.spheres-test
  (:require [rays.spheres :as sut]
            [rays.rays :as r]
            [clojure.test :refer :all]
            [rays.tuples :as t]
            [rays.common :as c]))


(deftest spheres-test
  (testing "a ray intersects a sphere at two points"
    (let [r  (r/ray (t/->point 0 0 -5)
                    (t/->vect 0 0 1))
          s  (sut/sphere)
          xs (sut/intersect s r)]
      (is (= 2 (count xs)))
      (is (c/eq-floats? 4.0 (first xs)))
      (is (c/eq-floats? 6.0 (second xs)))))

  (testing "a ray intersects a sphere at a tangent"
    (let [r  (r/ray (t/->point 0 1 -5)
                    (t/->vect 0 0 1))
          s  (sut/sphere)
          xs (sut/intersect s r)]
      (is (= 2 (count xs)))
      (is (c/eq-floats? 5.0 (first xs)))
      (is (c/eq-floats? 5.0 (second xs)))))

  (testing "a ray misses a sphere"
    (let [r  (r/ray (t/->point 0 2 -5)
                    (t/->vect 0 0 1))
          s  (sut/sphere)
          xs (sut/intersect s r)]
      (is (= 0 (count xs)))))

  (testing "a ray originates inside a sphere"
    (let [r  (r/ray (t/->point 0 0 0)
                    (t/->vect 0 0 1))
          s  (sut/sphere)
          xs (sut/intersect s r)]
      (is (= 2 (count xs)))
      (is (c/eq-floats? -1 (first xs)))
      (is (c/eq-floats? 1 (second xs)))))

  (testing "a sphere is behind a ray"
    (let [r  (r/ray (t/->point 0 0 5)
                    (t/->vect 0 0 1))
          s  (sut/sphere)
          xs (sut/intersect s r)]
      (is (= 2 (count xs)))
      (is (c/eq-floats? -6 (first xs)))
      (is (c/eq-floats? -4 (second xs))))))
