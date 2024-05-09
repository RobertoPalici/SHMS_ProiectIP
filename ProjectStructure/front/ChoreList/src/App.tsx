import './App.css'
import React, { useState, useEffect } from 'react';
import  Nav  from './components/header/Nav'
import {Top} from './components/Top'
import Mid from './components/Mid'
import { ChoreProps } from './components/ChoreLists';
import {Footer} from './components/Footer';


const App: React.FC = () => {

  const API_URL = 'http://localhost:8081/chores';

  const [chores, setChores] = useState<ChoreProps>({choreList: []});
  const [newChore, setNewChore] = useState('');
  const [newDesc, setNewDesc] = useState('');
  const [fetchError, setFetchError] = useState<any>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchItems = async () => {
      try{
        const response = await fetch(API_URL);
        if(!response.ok) throw Error('Data was not received');
        const listChores = await response.json();
        console.log(listChores);
        setChores(prevChores => {
          return {...prevChores, choreList: listChores.choreList};
        });
        setFetchError(null);
      } catch(err : unknown){
          if (err instanceof Error) {
            setFetchError(err.message);
          } else {
            setFetchError('An unknown error occurred');
          }
      } finally{
        setLoading(false);
      }
    }
    setTimeout(() => {
      (async () => await fetchItems())();
    }, 2000);
  }, [])

  return (
    <div className="App">
      <Nav />
      <Top />
      <main>
        {loading && <p>Inventory is loading...</p>}
        {fetchError && <p style={{color: "red"}}>{`Error: ${fetchError}`}</p>}
        {!fetchError && !loading && <Mid 
        chores = {chores}
        newChore = {newChore}
        newDesc = {newDesc}
        fetchError = {fetchError}
        setChores = {setChores}
        setNewChore = {setNewChore}
        setNewDesc = {setNewDesc}
        setFetchError = {setFetchError}
        />}
      </main>
      <Footer />  
    </div>
  );
}

export default App;
