(ns rays.spheres-test
  (:require [rays.spheres :as sut]
            [rays.rays :as r]
            [clojure.test :refer :all]
            [rays.tuples :as t]
            [rays.common :as c]
            [rays.intersections :as i]
            [rays.matrix :as m]))


(deftest spheres-test
  (testing "a ray intersects a sphere at two points"
    (let [r  (r/ray (t/->point 0 0 -5)
                    (t/->vect 0 0 1))
          s  (sut/sphere)
          xs (sut/intersect s r)]
      (is (= 2 (count xs)))
      (is (c/eq-floats? 4.0 (i/t (first xs))))
      (is (c/eq-floats? 6.0 (i/t (second xs))))))

  (testing "a ray intersects a sphere at a tangent"
    (let [r  (r/ray (t/->point 0 1 -5)
                    (t/->vect 0 0 1))
          s  (sut/sphere)
          xs (sut/intersect s r)]
      (is (= 2 (count xs)))
      (is (c/eq-floats? 5.0 (i/t (first xs))))
      (is (c/eq-floats? 5.0 (i/t (second xs))))))

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
      (is (c/eq-floats? -1 (i/t (first xs))))
      (is (c/eq-floats? 1 (i/t (second xs))))))

  (testing "a sphere is behind a ray"
    (let [r  (r/ray (t/->point 0 0 5)
                    (t/->vect 0 0 1))
          s  (sut/sphere)
          xs (sut/intersect s r)]
      (is (= 2 (count xs)))
      (is (c/eq-floats? -6 (i/t (first xs))))
      (is (c/eq-floats? -4 (i/t (second xs))))))

  (testing "a sphere's default transformation"
    (let [s (sut/sphere)]
      (is (m/eq (sut/get-transform s) m/ident))))

  (testing "changing a sphere's transformation"
    (let [s (sut/sphere)
          t (m/translation 2 3 4)
          s (sut/set-transform s t)]
      (is (= t
             (sut/get-transform s)))))

  (testing "intersecting a scaled sphere with a ray"
    (let [r (r/ray (t/->point 0 0 -5)
                   (t/->vect 0 0 1))
          s (sut/sphere)
          s (sut/set-transform s (m/scaling 2 2 2))
          xs (sut/intersect s r)]
      (is (= 2 (count xs)))
      (is (c/eq-floats? 3 (:t (first xs))))
      (is (c/eq-floats? 7 (:t (second xs)))))))
