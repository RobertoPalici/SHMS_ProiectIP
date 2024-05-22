import React, { useState, useEffect, useRef } from 'react';
import styles from './Mid.module.css'
import placeholder from './pictures/sweep.jpg'
import APIRequest from '../APIRequest';
import Chore, { ChoreProps } from './Chore';

interface MidProps{
  onData:(updatedChore: string) => void;
  chores: ChoreProps;
  handleSubmit: (e : React.FormEvent<HTMLFormElement>) => void;
  handleSubmitUpdate: (e : React.FormEvent<HTMLFormElement>, oldName: string) => void;
  handleDelete: (name : string) => void;
  handleUpdate: (oldName: string, updatedName: string, description: string, duration: string) => void;
  setNewChore: React.Dispatch<React.SetStateAction<string>>;
  setNewDesc: React.Dispatch<React.SetStateAction<string>>;
  setNewDuration: React.Dispatch<React.SetStateAction<string>>;
  setUpdatedChore: React.Dispatch<React.SetStateAction<string>>;
  setUpdatedDesc: React.Dispatch<React.SetStateAction<string>>;
  setUpdatedDuration: React.Dispatch<React.SetStateAction<string>>;
}

const ChoreList: React.FC<MidProps> = ({onData, chores, handleDelete, handleSubmit, handleSubmitUpdate, handleUpdate, setNewChore, setNewDesc, setNewDuration, setUpdatedChore, setUpdatedDesc, setUpdatedDuration}) => {

  const {choresList} = chores;
  const [history, setHistory] = useState(false)
  const handleHistory = () => {
    setHistory(!history);
  }

  return (
    <div className={styles.midsection}>
          <Chore
              onData={onData}
              name = {null}
              imageSrc={null}
              description={"No details"}
              personID={-1}
              duration={"No deadline"}
              handleSubmit={handleSubmit}
              handleSubmitUpdate={handleSubmitUpdate}
              handleDelete={handleDelete}
              handleUpdate={handleUpdate}
              setNewChore={setNewChore}
              setNewDesc={setNewDesc}
              setNewDuration={setNewDuration}
              setUpdatedChore={setUpdatedChore}
              setUpdatedDuration={setUpdatedDuration}
              setUpdatedDesc={setUpdatedDesc}
            />
             <button className={styles.historyButton} onClick={handleHistory}>History</button>
            {history && 
            <div className={styles.historyContainer}>
              <h2>Chore History</h2>
              <button>Clear History</button>
              <div className={styles.history_chores_css}>
                <div className={styles.hChoreTile}>
                  <img src={placeholder} alt=""></img>
                  <div className={styles.hbottom}>
                    <div className={styles.htextStyle}>
                      <h3>Titlu</h3>
                      <div className={styles.hdetails}>
                        <h3>Details</h3>
                        <p>Descriere</p>
                    </div>
                    <div className={styles.hduration}>
                      <h3>Deadline</h3>
                      <p>Marti la 10am</p>
                    </div>
                </div>
                <button className={styles.addFromHistory}>Add chore</button>
              </div>
            </div>
            <div className={styles.hChoreTile}>
                  <img src={placeholder} alt=""></img>
                  <div className={styles.hbottom}>
                    <div className={styles.htextStyle}>
                      <h3>Titlu</h3>
                      <div className={styles.hdetails}>
                        <h3>Details</h3>
                        <p>Descriere</p>
                    </div>
                    <div className={styles.hduration}>
                      <h3>Deadline</h3>
                      <p>Marti la 10am</p>
                    </div>
                </div>
                <button className={styles.addFromHistory}>Add chore</button>
              </div>
            </div>
            <div className={styles.hChoreTile}>
                  <img src={placeholder} alt=""></img>
                  <div className={styles.hbottom}>
                    <div className={styles.htextStyle}>
                      <h3>Titlu</h3>
                      <div className={styles.hdetails}>
                        <h3>Details</h3>
                        <p>Descriere</p>
                    </div>
                    <div className={styles.hduration}>
                      <h3>Deadline</h3>
                      <p>Marti la 10am</p>
                    </div>
                </div>
                <button className={styles.addFromHistory}>Add chore</button>
              </div>
            </div>
            <div className={styles.hChoreTile}>
                  <img src={placeholder} alt=""></img>
                  <div className={styles.hbottom}>
                    <div className={styles.htextStyle}>
                      <h3>Titlu</h3>
                      <div className={styles.hdetails}>
                        <h3>Details</h3>
                        <p>Descriere</p>
                    </div>
                    <div className={styles.hduration}>
                      <h3>Deadline</h3>
                      <p>Marti la 10am</p>
                    </div>
                </div>
                <button className={styles.addFromHistory}>Add chore</button>
              </div>
            </div>
            <div className={styles.hChoreTile}>
                  <img src={placeholder} alt=""></img>
                  <div className={styles.hbottom}>
                    <div className={styles.htextStyle}>
                      <h3>Titlu</h3>
                      <div className={styles.hdetails}>
                        <h3>Details</h3>
                        <p>Descriere</p>
                    </div>
                    <div className={styles.hduration}>
                      <h3>Deadline</h3>
                      <p>Marti la 10am</p>
                    </div>
                </div>
                <button className={styles.addFromHistory}>Add chore</button>
              </div>
            </div>
            </div>
            
            </div>
            }
        <div className={styles.chores_css}>
        {choresList?.map((chore, index) => (
          <div className={styles.choreTile} key={index}>
            <Chore
              onData={onData}
              name = {chore.name}
              imageSrc={placeholder}
              description={chore.description}
              personID={chore.personID}
              duration={chore.duration}
              handleSubmit={handleSubmit}
              handleSubmitUpdate={handleSubmitUpdate}
              handleDelete={handleDelete}
              handleUpdate={handleUpdate}
              setNewChore={setNewChore}
              setNewDesc={setNewDesc}
              setNewDuration={setNewDuration}
              /*updatedChore={chore.name ?? ''} 
              updatedDesc={chore.description}
              updatedDuration={chore.duration}*/
              setUpdatedChore={setUpdatedChore}
              setUpdatedDuration={setUpdatedDuration}
              setUpdatedDesc={setUpdatedDesc}
            />
          </div>
        ))}
      </div>
    </div>
  )

}
export default ChoreList;