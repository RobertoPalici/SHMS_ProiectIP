import React, { useState, useEffect, useRef } from 'react';
import styles from './Mid.module.css'
import placeholder from './pictures/sweep.jpg'
import APIRequest from '../APIRequest';
import Chore, { ChoreProps } from './Chore';

interface MidProps{
  chores: ChoreProps;
  handleSubmit: (e : React.FormEvent<HTMLFormElement>) => void;
  handleDelete: (name : string) => void;
  setNewChore: React.Dispatch<React.SetStateAction<string>>;
  setNewDesc: React.Dispatch<React.SetStateAction<string>>;
}

const ChoreList: React.FC<MidProps> = ({chores, handleDelete, handleSubmit, setNewChore, setNewDesc}) => {

  const {choresList} = chores;

  return (
    <div className={styles.midsection}>
          <Chore
              name = {null}
              imageSrc={null}
              description={"No details"}
              personID={-1}
              duration={-1}
              handleSubmit={handleSubmit}
              handleDelete={handleDelete}
              setNewChore={setNewChore}
              setNewDesc={setNewDesc}
            />
        <div className={styles.chores_css}>
        {choresList?.map((chore, index) => (
          <div className={styles.choreTile} key={index}>
            <Chore
              name = {chore.name}
              imageSrc={placeholder}
              description={chore.description}
              personID={chore.personID}
              duration={chore.duration}
              handleSubmit={handleSubmit}
              handleDelete={handleDelete}
            />
          </div>
        ))}
      </div>
    </div>
  )

}
export default ChoreList;