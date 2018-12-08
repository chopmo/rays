(ns rays.matrix-test
  (:require [rays.matrix :as sut]
            [rays.common :as cm]
            [clojure.test :refer [deftest testing is]]
            [rays.tuples :as t]))

(defn test-cases [m cases]
  (doseq [[r c expected :as case] cases]
    (is (cm/eq-floats? expected (sut/at m r c))
        (str case))))

(deftest test-matrix
  (testing "constructing and inspecting a 4x4 matrix"
    (let [m     (sut/->mat4 1    2    3    4
                            5.5  6.5  7.5  8.5
                            9    10   11   12
                            13.5 14.5 15.5 16.5)
          cases [[0 0 1]
                 [1 0 5.5]
                 [1 2 7.5]
                 [2 2 11]
                 [3 0 13.5]
                 [3 2 15.5]]]

      (test-cases m cases)))

  (testing "a 2x2 matrix ought to be representable"
    (let [m     (sut/->mat2 -3 5
                            1 -2)
          cases [[0 0 -3]
                 [0 1 5]
                 [1 0 1]
                 [1 1 -2]]]

      (test-cases m cases)))

  (testing "a 3x3 matrix ought to be representable"
    (let [m     (sut/->mat3 -3 5 0
                            1 -2 -7
                            0 1 1)
          cases [[0 0 -3]
                 [1 1 -2]
                 [2 1 1]]]

      (test-cases m cases)))

  (testing "matrix equality with identical matrices"
    (let [m1 (sut/->mat4 1 2 3 4
                         5 6 7 8
                         9 8 7 6
                         5 4 3 2)
          m2 (sut/->mat4 1 2 3 4
                         5 6 7 8
                         9 8 7 6
                         5 4 3 2)]
      (is (sut/eq m1 m2))))

  (testing "matrix equality with different matrices"
    (let [m1 (sut/->mat4 1 2 3 4
                         5 6 7 8
                         9 8 7 6
                         5 4 3 2)
          m2 (sut/->mat4 2 3 4 5
                         6 7 8 9
                         8 7 6 5
                         4 3 2 1)]
      (is (not (sut/eq m1 m2)))))

  (testing "multiplying two matrices"
    (let [a (sut/->mat4 1 2 3 4
                        5 6 7 8
                        9 8 7 6
                        5 4 3 2)
          b (sut/->mat4 -2 1 2 3
                        3 2 1 -1
                        4 3 6 5
                        1 2 7 8)]
      (is (sut/eq (sut/->mat4 20 22 50 48
                              44 54 114 108
                              40 58 110 102
                              16 26 46 42)
                  (sut/mul4 a b)))))

  (testing "multiplying a matrix with a tuple"
    (let [a (sut/->mat4 1 2 3 4
                        2 4 4 2
                        8 6 4 1
                        0 0 0 1)
          b (t/->tuple 1 2 3 1)]
      (is (t/equal? (t/->tuple 18 24 33 1)
                    (sut/mul-tuple a b)))))

  (testing "multiplying a matrix by the identity matrix"
    (let [a (sut/->mat4 0  1  2  4
                        1  2  4  8
                        2  4  8  16
                        4  8  16 32)]
      (is (sut/eq a (sut/mul4 a sut/identity)))))

  (testing "multiplying the identity matrix by a tuple"
    (let [a (t/->tuple 1 2 3 4)]
      (is (t/equal? a (sut/mul-tuple sut/identity a))))))
