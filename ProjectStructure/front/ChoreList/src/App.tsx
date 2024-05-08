import './App.css'
import  Nav  from './components/header/Nav'
import {Top} from './components/Top'
import {Mid} from './components/Mid'
import footer from './components/pictures/Footer.png';
import {Footer} from './components/Footer';


function App() {

  return (
    <div className="App">
      <Nav />
      <Top />
      <Mid />
      <Footer />
      
    </div>
  );
}

export default App;
