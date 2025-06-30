import React, {useContext, useEffect} from 'react';
import { move, newGame } from "../API";
import { StoreContext } from '../store/store';
import Stone from './Stone';


export default function Field(): React.JSX.Element {
  const {game, actions} = useContext(StoreContext);
  const rows = 7;
  const cells = 7;

  function updateBoard(colIndex: number, id:string): void {
    move(colIndex, id).then((response: any): void => {
      processResponse(response);    
    }).catch((error): void => {
      actions.setGame(error)
    });
  }

  function processResponse(response: any): void {
    if (response.status === 200) {
      actions.setGame(response.data);
    } else {
      actions.setGame(response);
    }
  }

  useEffect(() => {
    newGame().then((response: any): void => {
      console.log(response);
      processResponse(response);
    }).catch((error): void => {
      actions.setGame(error)
    });
  }, []);
  
  function getColorClass(token: string): string | undefined{
    switch (token) {
      case 'X':
        return "stone red";
      case 'O':
        return "stone yellow";
      case undefined:
        return '';
    }
  }

  

   return (
  <table className="playing-field">
    {Array.from({ length: rows }, (_, rowIndex) => (
      <tr key={`${rowIndex}`}>
        {Array.from({ length: cells }, (_, cellIndex) => (
          <td key={`${cellIndex}`}>
            {rowIndex === 6 ? (
              <div>
                <button 
                  onClick={() => updateBoard(cellIndex, game.id)} 
                  className="header-button"
                >
                  Stein in Spalte {cellIndex + 1} legen
                </button>
              </div>
            ) 
            :(
            (game.board && game.board[rowIndex] && game.board[rowIndex][cellIndex] !== undefined) ? (
              <div style={{ display: "flex", justifyContent: "center" }}>
                <Stone colorClass={getColorClass(game.board[rowIndex][cellIndex])} />
               </div>
            ) : (
              <div>undefined</div>
            ))
            }
          </td>
        ))}
      </tr>
    ))}
  </table>
);
}