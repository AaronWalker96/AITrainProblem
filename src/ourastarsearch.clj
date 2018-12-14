;; best first search mechanism;; based on earlier breadth-1st search
;; @args start start state
;; @args goal either a predicate to take a state determine if it is a goal
;;            or a state equal to the goal
;; @args LMG  legal move generator function which takes one state & returns
;;            a list of states (typically these new states will have a cost
;;            associated with them)
;; @args selector takes list of states & selects the next one to explore from
;; @args get-state removes cost information from a state returning the raw state
;; @args get-cost  removes other information from a state returning only the cost
;; @args debug prints some information

;;(A*search {:state 'a, :cost 0} (fn [x] (= x 'h)) a*lmg-map)

(defn A*search
  [start goal LMG & {:keys [get-state get-cost selector debug]
                     :or   {get-state :state
                            get-cost  :cost
                            selector  :undef
                            debug     false}}]
  (let [goal? (if (fn? goal)
                #(when (goal %) %)
                #(when (= % goal) %))
        member? (fn [lis x] (some (partial = x) lis))
        selector (if-not (= selector :undef)                ;it was a key arg
                   selector                                 ; se leave it as is, else set it as default
                   (fn [bag] (first (sort-by (comp get-cost first) bag))))]

    (loop [queued `((~start))
           visited nil]

      (if (empty? queued) nil             ;; fail if (null queued)
        (let [next (selector queued)      ;; select next node
              state (first next)          ;; filter out path
              raw-state (get-state state)
              ]                           ;; filter costs, etc

          (when debug (println 'selecting next '=> raw-state))
          (cond
            ;(and (fn? goal) (goal raw-state))     ;; is goal a predicate & goal found
            ;(reverse next)                        ;; quit with result

            (goal? raw-state)             ;; goal found
            (reverse next)                ;; quit with result

            :else
            (if (member? visited raw-state)
              (recur (remove #(= % next) queued) visited)
              (let [queued (remove #(= % next) queued)
                    moves (LMG state)
                    new-visited (cons raw-state visited)
                    new-states (map #(cons % next)
                                    (remove #(member? visited (get-state %))
                                            moves))]

                (when debug
                  (println 'exploring state '=> raw-state
                           'path next
                           'moves moves))


                (recur
                  (concat queued new-states)
                  new-visited)))))))))



;;====================== Map 1 ========================

(defn map1 [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (cond
      (= n 'station-one) (list

                           {:state 'station-two, :cost (+ c 3)}


                           )
      (= n 'station-two) (list
                           {:state 'station-one, :cost (+ c 3)}

                           {:state 'station-three, :cost (+ c 3)}

                           )
      (= n 'station-three) (list
                             {:state 'station-two, :cost (+ c 3)}

                             )
      )
    )
  )

;;====================== Map 2 ========================

(defn map2 [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (cond
      (= n 'station-one) (list

                           {:state 'station-two, :cost (+ c 4.5)}

                           {:state 'station-three, :cost (+ c 6)}

                           )
      (= n 'station-two) (list
                           {:state 'station-one, :cost (+ c 4.5)}

                           {:state 'station-three, :cost (+ c 5.7)}

                           )
      (= n 'station-three) (list
                             {:state 'station-one, :cost (+ c 6)}

                             {:state 'station-two, :cost (+ c 5.7)}

                             )
      )
    )
  )

;;====================== Map 3 ========================

(defn map3 [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (cond
      (= n 'station-one) (list

                           {:state 'station-two, :cost (+ c 4)}

                           {:state 'station-three, :cost (+ c 2.8)}

                           {:state 'station-four, :cost (+ c 4)}

                           )
      (= n 'station-two) (list
                           {:state 'station-one, :cost (+ c 4)}

                           {:state 'station-three, :cost (+ c 2.8)}

                           {:state 'station-five, :cost (+ c 4)}

                           )
      (= n 'station-three) (list
                             {:state 'station-one, :cost (+ c 2.8)}

                             {:state 'station-two, :cost (+ c 2.8)}

                             {:state 'station-four, :cost (+ c 2.8)}

                             {:state 'station-five, :cost (+ c 2.8)}

                             )
      (= n 'station-four) (list
                            {:state 'station-one, :cost (+ c 4)}

                            {:state 'station-three, :cost (+ c 2.8)}

                            {:state 'station-five, :cost (+ c 4)}

                            )
      (= n 'station-five) (list

                            {:state 'station-two, :cost (+ c 4)}

                            {:state 'station-three, :cost (+ c 2.8)}

                            {:state 'station-four, :cost (+ c 4)}

                            )
      )
    )
  )

;;====================== Map 4 ========================

(defn map4 [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (cond
      (= n 'station-one) (list

                           {:state 'station-two, :cost (+ c 3.2)}

                           )
      (= n 'station-two) (list
                           {:state 'station-one, :cost (+ c 3.2)}

                           {:state 'station-three, :cost (+ c 3.6)}

                           {:state 'station-four, :cost (+ c 3.6)}

                           )
      (= n 'station-three) (list
                             {:state 'station-two, :cost (+ c 3.6)}

                             )
      (= n 'station-four) (list
                            {:state 'station-two, :cost (+ c 3.6)}

                            )
      )
    )
  )

;;====================== Map 5 ========================

(defn map5 [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (cond
      (= n 'station-one) (list

                           {:state 'station-two, :cost (+ c 2.8)}

                           )
      (= n 'station-two) (list
                           {:state 'station-one, :cost (+ c 2.8)}

                           {:state 'station-three, :cost (+ c 3)}


                           )
      (= n 'station-three) (list
                             {:state 'station-two, :cost (+ c 3)}

                             {:state 'station-four, :cost (+ c 3)}

                             )
      (= n 'station-four) (list
                            {:state 'station-three, :cost (+ c 3)}

                            {:state 'station-five, :cost (+ c 2.8)}

                            )
      (= n 'station-five) (list

                            {:state 'station-four, :cost (+ c 2.8)}

                            )
      )
    )
  )

;;====================== Map 6 ========================

(defn map6 [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (cond
      (= n 'station-one) (list

                           {:state 'station-two, :cost (+ c 2)}

                           )
      (= n 'station-two) (list
                           {:state 'station-one, :cost (+ c 2)}

                           {:state 'station-three, :cost (+ c 2)}


                           )
      (= n 'station-three) (list
                             {:state 'station-two, :cost (+ c 2)}

                             )
      (= n 'station-four) (list
                            {:state 'station-five, :cost (+ c 2)}

                            )
      (= n 'station-five) (list

                            {:state 'station-four, :cost (+ c 2)}

                            )
      )
    )
  )

;;====================== Map 7 ========================

(defn map7 [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (cond
      (= n 'station-one) (list

                           {:state 'station-one, :cost (+ c 4)}

                           {:state 'station-two, :cost (+ c 4)}

                           )
      (= n 'station-two) (list
                           {:state 'station-one, :cost (+ c 4)}

                           {:state 'station-three, :cost (+ c 3)}


                           )
      (= n 'station-three) (list
                             {:state 'station-two, :cost (+ c 3)}

                             {:state 'station-four, :cost (+ c 3)}

                             )
      (= n 'station-four) (list
                            {:state 'station-three, :cost (+ c 3)}

                            )
      )
    )
  )

;;====================== Map 8 ========================

(defn map8 [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (cond
      (= n 'station-one) (list

                           {:state 'station-two, :cost (+ c 2.2)}

                           {:state 'station-five, :cost (+ c 2.2)}

                           )
      (= n 'station-two) (list
                           {:state 'station-one, :cost (+ c 2.2)}

                           {:state 'station-three, :cost (+ c 3.2)}


                           )
      (= n 'station-three) (list
                             {:state 'station-two, :cost (+ c 3.2)}

                             {:state 'station-four, :cost (+ c 2.8)}

                             )
      (= n 'station-four) (list
                            {:state 'station-three, :cost (+ c 2.8)}

                            {:state 'station-five, :cost (+ c 2)}

                            )
      (= n 'station-five) (list
                            {:state 'station-one, :cost (+ c 2.2)}

                            {:state 'station-four, :cost (+ c 2)}

                            )
      )
    )
  )

;;====================== Map 9 ========================

(defn map9 [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (cond
      (= n 'station-one) (list

                           {:state 'station-two, :cost (+ c 4.8)}

                           )
      (= n 'station-two) (list
                           {:state 'station-one, :cost (+ c 4.8)}

                           {:state 'station-three, :cost (+ c 2)}


                           )
      (= n 'station-three) (list
                             {:state 'station-two, :cost (+ c 2)}

                             {:state 'station-four, :cost (+ c 2)}

                             )
      (= n 'station-four) (list
                            {:state 'station-three, :cost (+ c 2)}

                            {:state 'station-five, :cost (+ c 2.2)}

                            {:state 'station-six, :cost (+ c 2.8)}

                            )
      (= n 'station-five) (list
                            {:state 'station-four, :cost (+ c 2.2)}

                            )
      (= n 'station-six) (list
                           {:state 'station-four, :cost (+ c 2.8)}

                           {:state 'station-seven, :cost (+ c 3.2)}

                           )
      (= n 'station-seven) (list
                             {:state 'station-six, :cost (+ c 3.2)}

                             {:state 'station-eight, :cost (+ c 5.1)}

                             )
      (= n 'station-eight) (list
                             {:state 'station-seven, :cost (+ c 5.1)}

                             {:state 'station-nine, :cost (+ c 3.2)}

                             {:state 'station-ten, :cost (+ c 2.8)}

                             )
      (= n 'station-nine) (list
                            {:state 'station-eight, :cost (+ c 3.2)}

                            )
      (= n 'station-ten) (list
                           {:state 'station-eight, :cost (+ c 2.8)}

                           {:state 'station-evelen, :cost (+ c 2.8)}

                           {:state 'station-twelve, :cost (+ c 3)}

                           )
      (= n 'station-evelen) (list
                              {:state 'station-ten, :cost (+ c 2.8)}

                              )
      (= n 'station-twelve) (list
                              {:state 'station-ten
                                      , :cost (+ c 3)}

                              )
      )
    )
  )

;;====================== Map 12 ========================

(defn map12 [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (cond
      (= n 'station-plymouth) (list
                                {:state 'station-exeter, :cost (+ c 4.5)}
                                )

      (= n 'station-exeter) (list
                              {:state 'station-plymouth, :cost (+ c 4.5)}
                              {:state 'station-bristol, :cost (+ c 5.7)}
                              )

      (= n 'station-bristol) (list
                               {:state 'station-exeter, :cost (+ c 5.7)}
                               {:state 'station-cardiff, :cost (+ c 5.8)}
                               {:state 'station-derby, :cost (+ c 15.2)}
                               {:state 'station-swindon, :cost (+ c 4.2)}
                               {:state 'station-southampton, :cost (+ c 6.7)}
                               )

      (= n 'station-southampton) (list
                                   {:state 'station-bristol, :cost (+ c 6.7)}
                                   {:state 'station-brighton, :cost (+ c 5.1)}
                                   )

      (= n 'station-brighton) (list
                                {:state 'station-southampton, :cost (+ c 5.1)}
                                {:state 'station-london, :cost (+ c 8.2)}
                                )

      (= n 'station-london) (list
                              {:state 'station-brighton, :cost (+ c 8.2)}
                              {:state 'station-swindon, :cost (+ c 10.0)}
                              {:state 'station-coventry, :cost (+ c 8.5)}
                              {:state 'station-leicester, :cost (+ c 9.2)}
                              {:state 'station-peterborough, :cost (+ c 6.0)}
                              {:state 'station-norwich, :cost (+ c 8.5)}
                              )

      (= n 'station-swindon) (list
                               {:state 'station-bristol, :cost (+ c 4.2)}
                               {:state 'station-cardiff, :cost (+ c 8.0)}
                               {:state 'station-birmingham, :cost (+ c 9.2)}
                               {:state 'station-derby, :cost (+ c 11.4)}
                               {:state 'station-london, :cost (+ c 10.0)}
                               )

      (= n 'station-cardiff) (list
                               {:state 'station-swansea, :cost (+ c 4.5)}
                               {:state 'station-liverpool, :cost (+ c 21.0)}
                               {:state 'station-swindon, :cost (+ c 8.0)}
                               {:state 'station-bristol, :cost (+ c 5.8)}
                               )

      (= n 'station-swansea) (list
                               {:state 'station-cardiff, :cost (+ c 4.5)}
                               )

      (= n 'station-norwich) (list
                               {:state 'station-london, :cost (+ c 8.5)}
                               {:state 'station-peterborough, :cost (+ c 6.0)}
                               )

      (= n 'station-peterborough) (list
                                    {:state 'station-leicester, :cost (+ c 7.0)}
                                    {:state 'station-nottingham, :cost (+ c 4.2)}
                                    {:state 'station-leeds, :cost (+ c 13.3)}
                                    {:state 'station-york, :cost (+ c 16.0)}
                                    {:state 'station-norwich, :cost (+ c 6.0)}
                                    {:state 'station-london, :cost (+ c 6.0)}
                                    )

      (= n 'station-leicester) (list
                                 {:state 'station-stoke, :cost (+ c 8.9)}
                                 {:state 'station-derby, :cost (+ c 4.0)}
                                 {:state 'station-peterborough, :cost (+ c 7.0)}
                                 {:state 'station-london, :cost (+ c 9.2)}
                                 )

      (= n 'station-coventry) (list
                                {:state 'station-birmingham, :cost (+ c 8.5)}
                                {:state 'station-london, :cost (+ c 8.5)}
                                )

      (= n 'station-birmingham) (list
                                  {:state 'station-wolverhampton, :cost (+ c 3.0)}
                                  {:state 'station-coventry, :cost (+ c 8.5)}
                                  {:state 'station-swindon, :cost (+ c 9.2)}
                                  )

      (= n 'station-wolverhampton) (list
                                     {:state 'station-liverpool, :cost (+ c 11.2)}
                                     {:state 'station-birmingham, :cost (+ c 3.0)}
                                     )

      (= n 'station-derby) (list
                             {:state 'station-bristol, :cost (+ c 15.2)}
                             {:state 'station-sheffield, :cost (+ c 4.0)}
                             {:state 'station-nottingham, :cost (+ c 4.1)}
                             {:state 'station-leicester, :cost (+ c 4.0)}
                             {:state 'station-swindon, :cost (+ c 11.4)}
                             )

      (= n 'station-nottingham) (list
                                  {:state 'station-derby, :cost (+ c 4.1)}
                                  {:state 'station-peterborough, :cost (+ c 4.2)}
                                  )

      (= n 'station-stoke) (list
                             {:state 'station-manchester, :cost (+ c 5.0)}
                             {:state 'station-leicester, :cost (+ c 8.9)}
                             )

      (= n 'station-sheffield) (list
                                 {:state 'station-manchester, :cost (+ c 6.4)}
                                 {:state 'station-leeds, :cost (+ c 6.4)}
                                 {:state 'station-derby, :cost (+ c 4.0)}
                                 )

      (= n 'station-liverpool) (list
                                 {:state 'station-cardiff, :cost (+ c 21.0)}
                                 {:state 'station-wolverhampton, :cost (+ c 11.2)}
                                 {:state 'station-manchester, :cost (+ c 7.1)}
                                 )

      (= n 'station-manchester) (list
                                  {:state 'station-liverpool, :cost (+ c 7.1)}
                                  {:state 'station-preston, :cost (+ c 5.0)}
                                  {:state 'station-leeds, :cost (+ c 8.0)}
                                  {:state 'station-sheffield, :cost (+ c 6.4)}
                                  {:state 'station-stoke, :cost (+ c 5.0)}
                                  )

      (= n 'station-leeds) (list
                             {:state 'station-manchester, :cost (+ c 8.0)}
                             {:state 'station-york, :cost (+ c 3.6)}
                             {:state 'station-peterborough, :cost (+ c 13.3)}
                             {:state 'station-sheffield, :cost (+ c 6.4)}
                             )

      (= n 'station-preston) (list
                               {:state 'station-manchester, :cost (+ c 5.0)}
                               {:state 'station-glasgow, :cost (+ c 13.6)}
                               )

      (= n 'station-york) (list
                            {:state 'station-newcastle, :cost (+ c 7.6)}
                            {:state 'station-peterborough, :cost (+ c 13.3)}
                            {:state 'station-leeds, :cost (+ c 3.6)}
                            )

      (= n 'station-newcastle) (list
                                 {:state 'station-york, :cost (+ c 7.6)}
                                 {:state 'station-edinburgh, :cost (+ c 9.2)}
                                 )

      (= n 'station-edinburgh) (list
                                 {:state 'station-newcastle, :cost (+ c 9.2)}
                                 {:state 'station-glasgow, :cost (+ c 8.0)}
                                 )

      (= n 'station-glasgow) (list
                               {:state 'station-edinburgh, :cost (+ c 8.0)}
                               {:state 'station-preston, :cost (+ c 13.6)}
                               )
      )
    )
  )


;;====================== Testing ========================


(defn a*lmg [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (list

      {:state (+ n 1), :cost (+ c 2)}

      {:state (+ n 5), :cost (+ c 7)}

      {:state (* n 2), :cost (+ c 8)}

      )))

(defn a*lmg-map [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (cond
      (= n 'a) (list

                 {:state 'b, :cost (+ c 12)}

                 {:state 'c, :cost (+ c 10)}

                 {:state 'e, :cost (+ c 9)}

                 )
      (= n 'b) (list
                 {:state 'a, :cost (+ c 13)}

                 {:state 'c, :cost (+ c 10)}

                 {:state 'd, :cost (+ c 8)}

                 )
      (= n 'c) (list

                 {:state 'a, :cost (+ c 12)}

                 {:state 'b, :cost (+ c 11)}

                 {:state 'd, :cost (+ c 8)}

                 {:state 'f, :cost (+ c 6)}

                 )
      (= n 'd) (list

                 {:state 'b, :cost (+ c 11)}

                 {:state 'h, :cost (+ c 6)}

                 {:state 'c, :cost (+ c 10)}

                 )
      (= n 'e) (list

                 {:state 'a, :cost (+ c 14)}

                 {:state 'f, :cost (+ c 5)}

                 {:state 'g, :cost (+ c 7)}

                 )
      (= n 'f) (list

                 {:state 'c, :cost (+ c 11)}

                 {:state 'e, :cost (+ c 9)}

                 {:state 'g, :cost (+ c 8)}

                 )
      (= n 'g) (list

                 {:state 'e, :cost (+ c 9)}

                 {:state 'f, :cost (+ c 9)}

                 {:state 'h, :cost (+ c 5)}

                 )
      (= n 'h) (list

                 {:state 'g, :cost (+ c 10)}

                 {:state 'd, :cost (+ c 12)}

                 )
      )
    )
  )

(defn Dijkstra [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (cond
      (= n 'a) (list

                 {:state 'b, :cost (+ c 3)}

                 {:state 'c, :cost (+ c 2)}

                 {:state 'e, :cost (+ c 4)}

                 )
      (= n 'b) (list
                 {:state 'a, :cost (+ c 3)}

                 {:state 'c, :cost (+ c 2)}

                 {:state 'd, :cost (+ c 2)}

                 )
      (= n 'c) (list

                 {:state 'a, :cost (+ c 2)}

                 {:state 'b, :cost (+ c 2)}

                 {:state 'd, :cost (+ c 2)}

                 {:state 'f, :cost (+ c 3)}

                 )
      (= n 'd) (list

                 {:state 'b, :cost (+ c 2)}

                 {:state 'h, :cost (+ c 6)}

                 {:state 'c, :cost (+ c 2)}

                 )
      (= n 'e) (list

                 {:state 'a, :cost (+ c 4)}

                 {:state 'f, :cost (+ c 2)}

                 {:state 'g, :cost (+ c 2)}

                 )
      (= n 'f) (list

                 {:state 'c, :cost (+ c 3)}

                 {:state 'e, :cost (+ c 2)}

                 {:state 'g, :cost (+ c 3)}

                 )
      (= n 'g) (list

                 {:state 'e, :cost (+ c 2)}

                 {:state 'f, :cost (+ c 3)}

                 {:state 'h, :cost (+ c 5)}

                 )
      (= n 'h) (list

                 {:state 'g, :cost (+ c 5)}

                 {:state 'd, :cost (+ c 6)}

                 )
      )
    )
  )

(defn test-A [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (cond
      (= n 's) (list

                 {:state 'a, :cost (+ c 11)}

                 {:state 'd, :cost (+ c 13)}

                 )
      (= n 'a) (list
                 {:state 'b, :cost (+ c 8)}

                 )
      (= n 'b) (list

                 {:state 'c, :cost (+ c 14)}

                 )
      (= n 'd) (list

                 {:state 'e, :cost (+ c 10)}

                 )
      (= n 'e) (list

                 {:state 'g, :cost (+ c 4)}

                 )
      (= n 'c) (list

                 {:state 'g, :cost (+ c 8)}

                 )
      )
    )
  )

(defn test-D [state]

  (let [n (:state state)

        c (:cost state)

        ]

    (cond
      (= n 's) (list

                 {:state 'a, :cost (+ c 4)}

                 {:state 'e, :cost (+ c 5)}

                 {:state 'f, :cost (+ c 6.4)}

                 )
      (= n 'a) (list
                 {:state 'b, :cost (+ c 2.2)}

                 )
      (= n 'b) (list

                 {:state 'c, :cost (+ c 3.6)}

                 )
      (= n 'c) (list

                 {:state 'd, :cost (+ c 5)}

                 )
      (= n 'd) (list

                 {:state 'c, :cost (+ c 5)}

                 {:state 'end, :cost (+ c 5)}

                 )
      (= n 'e) (list

                 {:state 'g, :cost (+ c 2.8)}

                 )
      (= n 'f) (list

                 {:state 'g, :cost (+ c 2.8)}

                 {:state 'i, :cost (+ c 2.8)}

                 )
      (= n 'g) (list

                 {:state 'f, :cost (+ c 2.8)}

                 {:state 'h, :cost (+ c 3.6)}

                 )
      (= n 'h) (list

                 {:state 'end, :cost (+ c 4)}

                 )
      (= n 'i) (list

                 {:state 'd, :cost (+ c 2.8)}

                 )
      )
    )
  )


(defn remove-last [str]
  (.substring (java.lang.String. str) 0 (- (count str) 1))
  )

(defn stateAdapter [plannerOutput]
  (let [numMoves (count (clojure.string/split plannerOutput #"move"))
        count 2
        formatted (clojure.string/replace plannerOutput #"\)\(" "\\) \\(")
        ]
    (loop [x count end numMoves result []]
      (if (= x (+ numMoves 1)) result
      (when (<= x end)
        (def move (nth (clojure.string/split formatted #"move") (- x 1)))
        (def startState (nth (clojure.string/split move #" ") 3))
        (def goalState (nth (clojure.string/split move #" ") 2))
        (def startState (remove-last (apply str startState)))

        (def  temp (apply str  startState "," goalState))

        (recur (+ x 1) numMoves (conj result temp)))
      )
      )
    )
  )
