//The Password strength procedure is working as the follow:
//We have many cases to care about to know a password strength , so we will present a global variable score , and each case will add some points to score.
//At the end of the algorithm we will decide the password strength according to the score value.
//The cases we have are :
//
//If the password matches the username then BadPassword
//If the password is less than 4 characters then TooShortPassword
//Score += password length * 4
//Score -= repeated characters in the password ( 1 char repetition ) 
//Score -= repeated characters in the password ( 2 char repetition ) 
//Score -= repeated characters in the password ( 3 char repetition ) 
//Score -= repeated characters in the password ( 4 char repetition ) 
//If the password has 3 numbers then score += 5
//If the password has 2 special characters then score += 5
//If the password has upper and lower character then score += 10
//If the password has numbers and characters then score += 15
//If the password has numbers and special characters then score += 15
//If the password has special characters and characters then score += 15
//If the password is only characters then score -= 10
//If the password is only numbers then score -= 10
//If score > 100 then score = 100
//Now according to score we are going to decide the password strength
//
//If  0  < score < 34 then BadPassword
//If  34 < score < 68  then GoodPassword
//If 68 < score < 100 then StrongPassword

var shortPass = 'Too short'
var badPass = 'Weak'
var goodPass = 'Good'
var strongPass = 'Strong'



function passwordStrength(password)
{
    score = 0 
    
    //password < 4
    if (password.length < 4 ) { return shortPass }
    
    //password == username
    //if (password.toLowerCase()==username.toLowerCase()) return badPass
    
    //password length
    score += password.length * 4
    score += ( checkRepetition(1,password).length - password.length ) * 1
    score += ( checkRepetition(2,password).length - password.length ) * 1
    score += ( checkRepetition(3,password).length - password.length ) * 1
    score += ( checkRepetition(4,password).length - password.length ) * 1

    //password has 3 numbers
    if (password.match(/(.*[0-9].*[0-9].*[0-9])/))  score += 5 
    
    //password has 2 sybols
    if (password.match(/(.*[!,@,#,$,%,^,&,*,?,_,~].*[!,@,#,$,%,^,&,*,?,_,~])/)) score += 5 
    
    //password has Upper and Lower chars
    if (password.match(/([a-z].*[A-Z])|([A-Z].*[a-z])/))  score += 10 
    
    //password has number and chars
    if (password.match(/([a-zA-Z])/) && password.match(/([0-9])/))  score += 15 
    //
    //password has number and symbol
    if (password.match(/([!,@,#,$,%,^,&,*,?,_,~])/) && password.match(/([0-9])/))  score += 15 
    
    //password has char and symbol
    if (password.match(/([!,@,#,$,%,^,&,*,?,_,~])/) && password.match(/([a-zA-Z])/))  score += 15 
    
    //password is just a nubers or chars
    if (password.match(/^\w+$/) || password.match(/^\d+$/) )  score -= 10 
    
    //verifing 0 < score < 100
    if ( score < 0 )  score = 0 
    if ( score > 100 )  score = 100 
    
    if (score < 34 )  return badPass 
    if (score < 68 )  return goodPass
    return strongPass
}


// checkRepetition(1,'aaaaaaabcbc')   = 'abcbc'
// checkRepetition(2,'aaaaaaabcbc')   = 'aabc'
// checkRepetition(2,'aaaaaaabcdbcd') = 'aabcd'

function checkRepetition(pLen,str) {
    res = ""
    for ( i=0; i<str.length ; i++ ) {
        repeated=true
        for (j=0;j < pLen && (j+i+pLen) < str.length;j++)
            repeated=repeated && (str.charAt(j+i)==str.charAt(j+i+pLen))
        if (j<pLen) repeated=false
        if (repeated) {
            i+=pLen-1
            repeated=false
        }
        else {
            res+=str.charAt(i)
        }
    }
    return res
}