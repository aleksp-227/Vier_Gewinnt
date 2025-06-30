import React, { useContext } from "react";
import { newGame } from "../API";
import { StoreContext } from "../store/store";

function NewGame() {
  
  const {actions} = useContext(StoreContext);

  function createNewGame() {
    newGame().then((response: any): void => {
      console.log(response.data);
      actions.setGame(response.data);
    }).catch((error): void => {
      actions.setGame(error)
    });
  }

  return (
    <button onClick={createNewGame} className="ngbtn">
      Neues Spiel
    </button>
  );
}

export default NewGame;
