import React from 'react';
import styles from './Nav.module.css';
import linksData from './links.json';
import logoImage from '../icon/logo.png';
import symbolImage from '../icon/symbol.png'; // Importă imaginea pentru symbol.png

type Link = {
    label: string;
    href: string;
};

const links: Link[] = linksData.links;

const Links: React.FC<{ links: Link[] }> = ({ links }) => {
    return (
        <div className={styles['links-container']}>
            {links.map((link: Link) => {
                const linkClassName = link.label.toLowerCase().replace(' ', '-');
                return (
                    <div key={link.href} className={`${styles['link']} ${styles[linkClassName]}`} style={link.label === 'Account' ? { position: 'relative' } : {}}>
                        <a href={link.href}>
                            {link.label}
                        </a>
                        {link.label === 'Account' && 
                        <img 
                            src={symbolImage} 
                            alt="Symbol" 
                            style={{
                                position: 'absolute', 
                                left: window.innerWidth < 560 ? '-20px' : '-35px', 
                                top: window.innerWidth < 560 ? '2.5px' : '1px',
                            }} 
                        />
                        }
                    </div>
                )
            })}
        </div>
    )
};

const Nav: React.FC<{}> = () => {
    return (
        <nav className={styles.navbar} style={{ position: 'relative' }}>
            <div className={styles['logo-container']}>
                <img src={logoImage} alt="Logo" />
            </div>
            <Links links={links} />
        </nav>
    )
}

export default Nav; 