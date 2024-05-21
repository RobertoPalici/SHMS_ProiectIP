import React, { useState, useEffect, useRef } from 'react';
import styles from './Mid.module.css'
import placeholder from './pictures/sweep.jpg'
import APIRequest from '../APIRequest';
import ChoreLists from './ChoreLists';
import { ChoresList, ChoreProps } from './Chore';



interface MidProps {
  chores: ChoreProps;
  newChore: string;
  newDesc: string;
  fetchError: any;
  setChores: React.Dispatch<React.SetStateAction<ChoreProps>>;
  setNewChore: React.Dispatch<React.SetStateAction<string>>;
  setNewDesc: React.Dispatch<React.SetStateAction<string>>;
  setFetchError: React.Dispatch<any>; 
}

const Mid: React.FC<MidProps> = ({chores, newChore, newDesc, fetchError, setChores, setNewChore, setNewDesc, setFetchError}) =>{
    const API_URL = 'http://localhost:8081/chores';

    const saveChores = (newChores : ChoresList[]) => {
      console.log(chores);
      setChores(prevChores => {
        return {...prevChores, choresList: newChores};  
      });
    }
    
    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
      console.log(chores);
        e.preventDefault();
        if(!newChore) return;
        addChore(newChore, newDesc);
        setNewChore('');
        setNewDesc("No details");
    }

    const addChore = async (name: string, description: string) => {
      if(name.trim() === ''){
        alert('Please enter a title for the chore');
      } else {
        console.log(name);
        console.log(chores);
        console.log(chores.choresList);
        const newChoreItem = {id: undefined, name, description, personID: -1, duration: -1, imageSrc: '' };
        console.log(newChoreItem);
        if (!chores) {
          const listChores = [chores, newChoreItem];
          console.log(listChores);
          saveChores(listChores);
        }
        else{
        const listChores = [...chores.choresList, newChoreItem];
        console.log(listChores);
        saveChores(listChores);}
        const options = {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(newChoreItem)
        }
        const response = await APIRequest(`${API_URL}/addChore?name=${name}&description=${description}`, options);
        if(response)
          setFetchError(response);
      }
    }
    const handleDelete = async (name : string) => {
      if(chores.choresList !== undefined){
        const listChores = chores.choresList.filter((item) => item.name !== name);
        const targetChore = chores.choresList.filter((item) => item.name === name);
        const index = chores.choresList.findIndex(item => item.name === name); 
        saveChores(listChores);

        const options = {method: 'DELETE'};
        const response = await APIRequest(`${API_URL}/removeChore?id=${index}`, options);
        if(response)
          setFetchError(response);
      }
    }

    return(
      <ChoreLists
        chores={chores}
        handleDelete={handleDelete}
        handleSubmit={handleSubmit}
        setNewChore={setNewChore}
        setNewDesc={setNewDesc}
    />
  );
};

export default Mid;
