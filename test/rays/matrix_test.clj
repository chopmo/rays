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
      (is (sut/eq a (sut/mul4 a sut/ident)))))

  (testing "multiplying the identity matrix by a tuple"
    (let [a (t/->tuple 1 2 3 4)]
      (is (t/equal? a (sut/mul-tuple sut/ident a)))))

  (testing "transposing a matrix"
    (let [a (sut/->mat4 0 9 3 0
                        9 8 0 8
                        1 8 5 3
                        0 0 5 8)]
      (is (sut/eq (sut/->mat4 0 9 1 0
                              9 8 8 0
                              3 0 5 5
                              0 8 3 8)
                  (sut/transpose a)))))

  (testing "calculating the determinant of a 2x2 matrix"
    (let [a (sut/->mat2 1 5 -3 2)]
      (is (cm/eq-floats? 17
                         (sut/determinant a)))))

  (testing "a submatrix of a 3x3 matrix is a 2x2 matrix"
    (let [a (sut/->mat3 1 5 0
                        -3 2 7
                        0 6 -3)]
      (is (sut/eq (sut/->mat2 -3 2
                              0 6)
                  (sut/submatrix a 0 2)))))

  (testing "a submatrix of a 4x4 matrix is a 3x3 matrix"
    (let [a (sut/->mat4 -6 1 1 6
                        -8 5 8 6
                        -1 0 8 2
                        -7 1 -1 1)]
      (is (sut/eq (sut/->mat3 -6 1 6
                              -8 8 6
                              -7 -1 1)
                  (sut/submatrix a 2 1)))))

  (testing "calculating a minor of a 3x3 matrix"
    (let [a (sut/->mat3 3 5 0
                        2 -1 -7
                        6 -1 5)
          b (sut/submatrix a 1 0)]
      (is (cm/eq-floats? 25 (sut/determinant b)))
      (is (cm/eq-floats? 25 (sut/minor a 1 0)))))

  (testing "calculating a cofactor of a 3x3 matrix"
    (let [a (sut/->mat3 3 5 0
                        2 -1 -7
                        6 -1 5)]
      (is (cm/eq-floats? -12 (sut/minor a 0 0)))
      (is (cm/eq-floats? -12 (sut/cofactor a 0 0)))
      (is (cm/eq-floats? 25 (sut/minor a 1 0)))
      (is (cm/eq-floats? -25 (sut/cofactor a 1 0)))))

  (testing "calculating the determinant of a 3x3 matrix"
    (let [a (sut/->mat3 1 2 6
                        -5 8 -4
                        2 6 4)]
      (is (cm/eq-floats? 56 (sut/cofactor a 0 0)))
      (is (cm/eq-floats? 12 (sut/cofactor a 0 1)))
      (is (cm/eq-floats? -46 (sut/cofactor a 0 2)))
      (is (cm/eq-floats? -196 (sut/determinant a)))))

  (testing "calculating the determinant of a 4x4 matrix"
    (let [a (sut/->mat4 -2 -8 3 5
                        -3 1 7 3
                        1 2 -9 6
                        -6 7 7 -9)]
      (is (cm/eq-floats? 690 (sut/cofactor a 0 0)))
      (is (cm/eq-floats? 447 (sut/cofactor a 0 1)))
      (is (cm/eq-floats? 210 (sut/cofactor a 0 2)))
      (is (cm/eq-floats? 51 (sut/cofactor a 0 3)))
      (is (cm/eq-floats? -4071 (sut/determinant a)))))

  (testing "testing an invertible matrix for invertibility"
    (let [a (sut/->mat4 6 4 4 4
                        5 5 7 6
                        4 -9 3 -7
                        9 1 7 -6)]
      (is (cm/eq-floats? -2120 (sut/determinant a)))
      (is (sut/invertible? a))))

  (testing "testing a non-invertible matrix for invertibility"
    (let [a (sut/->mat4 -4 2 -2 -3
                        9 6 2 6
                        0 -5 1 -5
                        0 0 0 0)]
      (is (cm/eq-floats? 0 (sut/determinant a)))
      (is (not (sut/invertible? a)))))

  (testing "calculating the inverse of a matrix"
    (let [a (sut/->mat4 -5 2 6 -8
                        1 -5 1 8
                        7 7 -6 -7
                        1 -3 7 4)
          b (sut/inverse a)]
      (is (cm/eq-floats? 532 (sut/determinant a)))
      (is (cm/eq-floats? -160 (sut/cofactor a 2 3)))
      (is (cm/eq-floats? (float (/ -160 532))
                         (sut/at b 3 2)))
      (is (cm/eq-floats? 105 (sut/cofactor a 3 2)))
      (is (cm/eq-floats? (float (/ 105 532))
                         (sut/at b 2 3)))
      (is (sut/eq (sut/->mat4 0.21805 0.45113 0.24060 -0.04511
                              -0.80827 -1.45677 -0.44361 0.52068
                              -0.07895 -0.22368 -0.05263 0.19737
                              -0.52256 -0.81391 -0.30075 0.30639)
                  b))))

  (testing "multiplying by a translation matrix"
    (let [transform (sut/translation 5 -3 2)
          p         (t/->point -3 4 5)]
      (is (t/equal? (t/->point 2 1 7)
                    (sut/mul-tuple transform p)))))

  (testing "multiplying by the inverse of a translation matrix"
    (let [transform (sut/translation 5 -3 2)
          inv       (sut/inverse transform)
          p         (t/->point -3 4 5)]
      (is (t/equal? (t/->point -8 7 3)
                    (sut/mul-tuple inv p)))))

  (testing "translation does not affect vectors"
    (let [transform (sut/translation 5 -3 2)
          v         (t/->vect -3 4 5)]
      (is (t/equal? v
                    (sut/mul-tuple transform v)))))

  (testing "a scaling matrix applied to a point"
    (let [transform (sut/scaling 2 3 4)
          p (t/->point -4 6 8)]
      (is (t/equal? (t/->point -8 18 32)
                    (sut/mul-tuple transform p)))))

  (testing "a scaling matrix applied to a vector"
    (let [transform (sut/scaling 2 3 4)
          v (t/->vect -4 6 8)]
      (is (t/equal? (t/->vect -8 18 32)
                    (sut/mul-tuple transform v)))))

  (testing "multiplying by the inverse of a scaling matrix"
    (let [transform (sut/scaling 2 3 4)
          inv (sut/inverse transform)
          v (t/->vect -4 6 8)]
      (is (t/equal? (t/->vect -2 2 2)
                    (sut/mul-tuple inv v)))))

  (testing "reflection is scaling by a negative value"
    (let [transform (sut/scaling -1 1 1)
          p (t/->point 2 3 4)]
      (is (t/equal? (t/->point -2 3 4)
                    (sut/mul-tuple transform p))))))
