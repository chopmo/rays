(ns rays.spheres-test
  (:require [rays.spheres :as sut]
            [rays.rays :as r]
            [clojure.test :refer :all]
            [rays.tuples :as t]
            [rays.common :as c]
            [rays.intersections :as i]
            [rays.matrix :as m]
            [rays.spheres :as s]))


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
      (is (c/eq-floats? 7 (:t (second xs))))))

  (testing "intersecting a translated sphere with a ray"
    (let [r (r/ray (t/->point 0 0 -5)
                   (t/->vect 0 0 1))
          s (sut/sphere)
          s (sut/set-transform s (m/translation 5 0 0))
          xs (sut/intersect s r)]
      (is (empty? xs))))

  ;; Normals
  (testing "the normal on a sphere at a point on the x axis"
    (let [s (sut/sphere)
          n (sut/normal-at s (t/->point 1 0 0))]
      (is (t/equal? n (t/->vect 1 0 0)))))

  (testing "the normal on a sphere at a point on the y axis"
    (let [s (sut/sphere)
          n (sut/normal-at s (t/->point 0 1 0))]
      (is (t/equal? n (t/->vect 0 1 0)))))

  (testing "the normal on a sphere at a point on the z axis"
    (let [s (sut/sphere)
          n (sut/normal-at s (t/->point 0 0 1))]
      (is (t/equal? n (t/->vect 0 0 1)))))

  (testing "the normal on a sphere at a point on a nonaxial point"
    (let [s (sut/sphere)
          n (sut/normal-at s (t/->point (/ (Math/sqrt 3) 3)
                                        (/ (Math/sqrt 3) 3)
                                        (/ (Math/sqrt 3) 3)))]
      (is (t/equal? n (t/->vect (/ (Math/sqrt 3) 3)
                                (/ (Math/sqrt 3) 3)
                                (/ (Math/sqrt 3) 3))))))

  (testing "the normal is a normalized vector"
    (let [s (sut/sphere)
          n (sut/normal-at s (t/->point (/ (Math/sqrt 3) 3)
                                        (/ (Math/sqrt 3) 3)
                                        (/ (Math/sqrt 3) 3)))]
      (is (t/equal? n (t/normalize n)))))

  (testing "computing the normal on a translated sphere"
    (let [s (sut/sphere)
          s (s/set-transform s (m/translation 0 1 0))
          n (sut/normal-at s (t/->point 0 1.70711 -0.70711))]
      (is (t/equal? n (t/->vect 0 0.70711 -0.70711)))))

  (testing "computing the normal on a transformed sphere"
    (let [s (sut/sphere)
          m (m/mul4 (m/scaling 1 0.5 1)
                    (m/rotation-z (/ Math/PI 5)))
          s (sut/set-transform s m)
          n (sut/normal-at s (t/->point 0
                                        (/ (Math/sqrt 2) 2)
                                        (- (/ (Math/sqrt 2) 2))))]
      (is (t/equal? n (t/->vect 0 0.97014 -0.24254))))))
