type GreetProps = {
    name: string
    messageCount: number
    isLoggedIn: boolean
}
export const Greet = (props: GreetProps) => {
    return (
    <div>
        <h2>{props.isLoggedIn ? `Salut ${props.name}! Ai ${props.messageCount} mesaje!` : 'Welcome guest'}</h2>
        </div>
    )
        
}