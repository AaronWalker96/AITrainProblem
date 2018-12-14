; Ops-search exposed to the train problem
(require 'search.opsearch)

;=============;
;= Operators =;
;=============;
(def ops
  '{load {:pre ((train ?train)
                 (cargo ?cargo)
                 (cot ?train nil)
                 (tas ?train ?stn)
                 (cat ?cargo  ?stn))
          :add ((cot ?train ?cargo))
          :del ((cat ?cargo ?stn)
                 (cot ?train nil))
          :txt (?train loads ?cargo at ?stn)
          :cmd [load ?cargo]}

    unload {:pre  ((cot ?train ?cargo)
                    (tas ?train ?stn)
                    (:guard (? cargo)))
            :add ((cot ?train nil)
                   (cat ?cargo  ?stn))
            :del ((cot ?train ?cargo))
            :txt (?train unloads ?cargo at ?stn)
            :cmd [unload ?cargo]}

    move    {:pre ((train ?train)
                    (tas ?train ?stn)
                    (links ?stn ?dest))
             :add ((tas ?train ?dest))
             :del ((tas ?train ?stn))
             :txt (?train moves from ?stn to ?dest)
             :cmd [move ?dest]}})


;=============;
;=   Tests   =;
;=============;
; 1
(def world-one
  '#{(train t1)
     (cargo c1)
     (links s1 s2)
     (links s2 s1)
     (links s3 s2)
     (links s2 s3)})

(def state1
  '#{(tas t1 s1)
     (cot t1 nil)
     (cat c1 s3)})

; user=> (ops-search state1 '((cat c1 s2)) ops :world world-one)


; 2
(def world-two
  '#{(train t1)
     (cargo c1)
     (links s1 s2)
     (links s2 s1)
     (links s3 s1)
     (links s1 s3)
     (links s2 s3)
     (links s3 s2)})

(def state2
  '#{(tas t1 s1)
     (cot t1 nil)
     (cat c1 s1)})

; user=> (ops-search state2 '((cat c1 s2)) ops :world world-two)


; 3
(def world-three
  '#{(train t1)
     (cargo c1)
     (links s1 s2) (links s2 s1)
     (links s3 s1) (links s1 s3)
     (links s3 s2) (links s2 s3)
     (links s1 s4) (links s4 s1)
     (links s3 s4) (links s4 s3)
     (links s5 s4) (links s4 s5)
     (links s5 s2) (links s2 s5)
     (links s3 s5) (links s5 s3)
     })

(def state3
  '#{(tas t1 s1)
     (cot t1 nil)
     (cat c1 s2)})

; user=> (ops-search state3 '((cat c1 s4)) ops :world world-three)


; 4
(def world-four
  '#{(train t1)
     (cargo c1)
     (cargo c2)
     (links s1 s2) (links s2 s1)
     (links s2 s4) (links s4 s2)
     (links s3 s2) (links s2 s3)
     })

(def state4
  '#{(tas t1 s1)
     (cot t1 nil)
     (cat c1 s3)
     (cat c2 s4)})

; user=> (ops-search state4 '((cat c1 s2)(cat c2 s2)) ops :world world-four)


; 5
(def world-five
  '#{(train t1)
     (train t2)
     (cargo c1)
     (cargo c2)
     (links s1 s2) (links s2 s1)
     (links s2 s3) (links s3 s2)
     (links s3 s4) (links s4 s3)
     (links s4 s5) (links s5 s4)
     })

(def state5
  '#{(tas t1 s1)
     (tas t2 t5)
     (cot t1 nil)
     (cot t2 nil)
     (cat c1 s2)
     (cat c2 s4)})

; user=> (ops-search state5 '((cat c1 s4)(cat c2 s2)) ops :world world-five)
; Only moves one of the 2 trains


; 6
(def world-six
  '#{(train t1)
     (cargo c1)
     (cargo c2)
     (links s1 s2) (links s2 s1)
     (links s2 s3) (links s3 s2)
     (links s4 s5) (links s5 s4)
     })

(def state6
  '#{(tas t1 s1)
     (cot t1 nil)
     (cat c1 s3)
     (cat c2 s4)})

; user=> (ops-search state6 '((cat c1 s2) (cat c2 s5)) ops :world world-six)
; nil - Does not attempt to move c1, will only return nil


; 7a
; Start state = Goal state
(def world-seven-a
  '#{(train t1)
     (cargo c1)
     (links s1 s1) ; Represents a loop track
     (links s1 s2) (links s2 s1)
     (links s2 s3) (links s3 s2)
     (links s4 s3) (links s3 s4)
     })

(def state7a
  '#{(tas t1 s3)
     (cot t1 nil)
     (cat c1 s1)})

; user=> (ops-search state7a '((cat c1 s1)) ops :world world-seven-a)
; Goal state already achieved, no moves required


; 7b
; Start state = Goal state
(def world-seven-b
  '#{(train t1)
     (cargo c1)
     (links s1 s2) (links s2 s1)
     (links s2 s3) (links s3 s2)
     (links s4 s3) (links s3 s4)})

(def state7b
  '#{(tas t1 s3)
     (cot t1 nil)
     (cat c1 s1)})

; user=> (ops-search state7b '((cat c1 s1)) ops :world world-seven-b)
; Goal state already achieved, no moves required


; 8
(def world-eight
  '#{(cargo c1)
     (links s1 s2) (links s2 s1)
     (links s2 s3) (links s3 s2)
     (links s3 s4) (links s4 s3)
     (links s4 s5) (links s5 s4)
     (links s5 s1) (links s1 s5)
     })

(def state8
  '#{(cat c1 s3)})

; user=> (ops-search state8 '((cat c1 s4)) ops :world world-eight)
; nil - No train to move the cargo


; 9
(def world-nine
  '#{(cargo c1)
     (train t1)
     (links s1 s2) (links s2 s1)
     (links s2 s3) (links s3 s2)
     (links s3 s4) (links s4 s3)
     (links s4 s5) (links s5 s4)
     (links s5 s5)
     (links s4 s6) (links s6 s4)
     (links s6 s7) (links s7 s6)
     (links s7 s8) (links s8 s7)
     (links s8 s9) (links s9 s8)
     (links s8 s10) (links s10 s8)
     (links s10 s11) (links s11 s10)
     (links s10 s12) (links s12 s10)
     })

(def state9
  '#{(cat c1 s12)
     (tas t1 s1)
     (cot t1 nil)})

; user=> (ops-search state9 '((cat c1 s11)) ops :world world-nine)


;====================;
;= Additional Tests =;
;====================;
; AT 1
(def world_1at
  '#{(train t1)
     (cargo c1)
     (links s1 s2)
     (links s2 s1)})

(def state_1at
  '#{(tas t1 s1)
     (cot t1 nil)
     (cat c1 s2)})

; user=> (ops-search state_1at '((cat c1 s1) (cat c2 s2b) (cat c3 s1)) ops :world world_1at)


; AT 2
(def world_2at
  '#{(train t1)
     (cargo c1)
     (links s1 s2)
     (links s2 s1)
     (links s2 s3)
     (links s3 s2)})

(def state_2at
  '#{(tas t1 s1)
     (cot t1 nil)
     (cat c1 s3)})

; user=> (ops-search state_2at '((cat c1 s1) (cat c2 s2b) (cat c3 s1)) ops :world world_2at)


; AT 3
(def world_3at
  '#{(train t1)
     (train t2)
     (cargo c1)
     (cargo c2)
     (cargo c3)
     (links s1 s2a) (links s2a s1)
     (links s2a s2b) (links s2b s2a)
     (links s2a s3) (links s3 s2a)
     })

(def state_3at
  '#{(tas t1 s1)
     (tas t2 s3)
     (cot t1 nil)
     (cot t2 nil)
     (cat c1 s2b)
     (cat c2 s3)
     (cat c3 s3)})

; user=> (ops-search state_3at '((cat c1 s1) (cat c2 s2b) (cat c3 s1)) ops :world world_3at)
; takes conciderable time
; however, works uses 2 trains


; AT 4
(def world_4at
  '#{(train t1)
     (train t2)
     (cargo c1)
     (cargo c2)
     (cargo c3)
     (cargo c4)
     (links s1 s2a) (links s2a s1)
     (links s2a s2b) (links s2b s2a)
     (links s2a s3) (links s3 s2a)
     (links s3 s4a) (links s4a s3)
     (links s4a s4b) (links s4b s4a)
     (links s4a s5) (links s5 s4a)
     })

(def state_4at
  '#{(tas t1 s3)
     (tas t2 s5)
     (cot t1 nil)
     (cot t2 nil)
     (cat c1 s4b)
     (cat c2 s4b)
     (cat c3 s5)
     (cat c4 s5)})

; user=> (ops-search state_4at '((cat c1 s1) (cat c2 s1) (cat c3 s2b) (cat c4 s2b)) ops :world world_4at)
; StackOverflowError







; SCALETESTING SMALL MAP

(def world-scaletest-small-a
  '#{(train t1)
     (cargo c1)
     (links s1 s2) (links s2 s1)
     (links s3 s1) (links s1 s3)
     (links s3 s2) (links s2 s3)
     (links s1 s4) (links s4 s1)
     (links s3 s4) (links s4 s3)
     (links s5 s4) (links s4 s5)
     (links s5 s2) (links s2 s5)
     (links s3 s5) (links s5 s3)
     })

(def state3
  '#{(tas t1 s1)
     (cot t1 nil)
     (cat c1 s2)})

; user=> (ops-search state3 '((cat c1 s4)) ops :world world-scaletest-small-a)




