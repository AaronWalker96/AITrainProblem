

;; best first search mechanism
;; based on earlier breadth-1st search
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


(defn A*search
  [start goal LMG & {:keys [get-state get-cost selector debug]
                     :or {get-state :state
                          get-cost :cost
                          selector :undef
                          debug    false}}]
  (let [goal? (if (fn? goal)
                  #(when (goal %) %)
                  #(when (= % goal) %))
        member? (fn [lis x] (some (partial = x) lis))
        selector (if-not (= selector :undef)  ;it was a key arg
                    selector                  ; se leave it as is, else set it as default
                   (fn [bag]  (first (sort-by (comp get-cost first) bag))))]

      (loop [queued  `( (~start) )
             visited nil
             ]
        (if (empty? queued) nil                      ;; fail if (null queued)
          (let [next      (selector queued)          ;; select next node
                state     (first next)               ;; filter out path
                raw-state (get-state state)          ;; filter costs, etc
                ]
            (when debug (println 'selecting next '=> raw-state))
            (cond
              ;(and (fn? goal) (goal raw-state))     ;; is goal a predicate & goal found
              ;(reverse next)                        ;; quit with result

              (goal? raw-state)                      ;; goal found
              (reverse next)                         ;; quit with result

              :else
              (if (member? visited raw-state)
                (recur (remove #(= % next) queued) visited)
                (let [queued      (remove #(= % next) queued)
                      moves       (LMG state)
                      new-visited (cons raw-state visited)
                      new-states  (map #(cons % next)
                                    (remove #(member? visited (get-state %))
                                      moves))
                      ]
                  (when debug
                    (println 'exploring state '=> raw-state
                      'path      next
                      'moves     moves
                      ))

                  (recur
                    (concat queued new-states)
                    new-visited)
                  ))
              ))
          ))))


