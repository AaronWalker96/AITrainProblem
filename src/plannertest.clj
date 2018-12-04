;Experimentation with the planner to figure out how it works

(require 'search.planner)

(def start-state
  '#{
     (isa trainOne Train)
     (tas trainOne stationOne)
     (cas cargoOne stationTwo)
     })

(def ops

  '{
    :unload
            {:name unload
            :achieves (at ?Cargo ?Station)
            :when  ((is ?Cargo Cargo))
            :post ( (on ?Cargo ?Train)(at ?Train ?Station))
            :pre ()
            :del ( (on ?Cargo ?Train) )
            :add ( (at ?Cargo ?Station) )
            :cmd ( drop ?Cargo )
            :txt (?T drops ?Cargo at ?Station)
            }
    :load
           {:name load
            :achieves (on ?Cargo ?Train)
            :when ((is ?Cargo Cargo) )
            :post ((at ?Train ?Station)(at ?Cargo ?Station))
            :pre ()
            :del ( (at ?Cargo ?Station) )
            :add ( (on ?Cargo ?Train) )
            :cmd ( pickup ?Cargo )
            :txt (?C picked up at ?Station by ?Train)
            }
    :move
           {:name move
            :achieves ((at ?Train ?Station2))
            :when ((isa ?Train Train))
            :post ((at ?Train ?Station1))
            :pre ()
            :del ( (at ?Train ?Station1))
            :add ( (at ?Train ?Station2))
            :cmd ( move to ?Station2 )
            :txt (?Train moves to ?Station2)
            }
    }
  )

(def ops-alt

  '{
    :unload
    {:name unload
     :achieves ((cas ?Cargo ?Station) )
     :when  ((isa ?Train Train))
     :post ((cot ?Cargo ?Train)(tas ?Train ?Station))
     :pre ()
     :del ((cot ?Cargo ?Train))
     :add ((cas ?Cargo ?Station))
     :cmd ((drop ?Cargo))
     :txt ((?Train drops ?Cargo at ?Station))
     }
    :load
    {:name load
     :achieves ((cot ?Cargo ?Train))
     :when ()
     :post ((cas ?Cargo ?Station)(tas ?Train ?Station))
     :pre ()
     :del ((cas ?Cargo ?Station))
     :add ((cot ?Cargo ?Train) )
     :cmd ((pickup ?Cargo))
     :txt ((?Cargo picked up at ?Station by ?Train))
     }
    :move
    {:name move
     :achieves ((tas ?Train ?Dstn))
     :when ()
     :post ()
     :pre ((tas ?Train ?Src))
     :del ((tas ?Train ?Src))
     :add ((tas ?Train ?Dstn))
     :cmd ((move to ?Dstn) )
     :txt ((?Train moves to ?Dstn))
     }
    }
  )


(def ss3b
  '#{(station london)
     (station ncl)
     (isa ncl-exp train)
     (tas ncl-exp london)
     (cas llama ncl)
     })

(def ops3
  '{
    :unload
    {:name unload
     :achieves (cas ?stuff ?stn)
     :when  ((isa ?t train))
     :post ((cot ?stuff ?t)(tas ?t ?stn))
     :pre ()
     :del ( (cot ?stuff ?t))
     :add ( (cas ?stuff ?stn) )
     :cmd ((drop ?stuff)  )
     :txt ((?t drops ?stuff at ?stn) )
     }
    :load
    {:name load
     :achieves (cot ?stuff ?t)
     :when ((cas ?stuff ?stn))
     :post ((tas ?t ?stn))
     :pre ()
     :del ((cas ?stuff ?stn) )
     :add ((cot ?stuff ?t) )
     :cmd ((pickup ?stuff))
     :txt ((?stuff picked up at ?stn by ?t))
     }
    :move
    {:name move
     :achieves (tas ?t ?dst)
     :when ((tas ?t ?src))
     :post ()
     :pre ()
     :del ( (tas ?t ?src))
     :add ( (tas ?t ?dst))
     :cmd ( (move ?t to ?dst) )
     :txt ((?t moves to ?dst))
     }
    }
  )
;(planner start-state '((cas cargoOne stationOne)) ops-alt)
;(planner ss3b '(cas llama london) ops3)

