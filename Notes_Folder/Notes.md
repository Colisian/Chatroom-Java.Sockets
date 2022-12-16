When a class implements a runable interface the instances of that class will be excuted by  aunique thread.
To make a new thread you must create a thread object (ClientHandler clienthandler = new ClientHandler()) and pass in a runnable object (ClientHandler(socket))

To Check that the Server is running, in powershell run "tnc localhost <port#>"


Definitions:

(1) try catch block: used for xception handling. The code (or set of statements) that can throw an exception is placed inside try block and if the exception is raised, it is handled by the corresponding catch block. A try block is always followed by a catch block or finally block, if exception occurs, the rest of the statements in the try block are skipped and the flow immediately jumps to the corresponding catch block.

try{
   //statements that may cause an exception
}catch(Exception e){
  //statements that will execute when exception occurs
}    

try{
   //statements that may cause an exception
}finally{
  //statements that execute whether the exception occurs or not
}    

(2) Nested try catch: When a try catch block is present in another try block then it is called the nested try catch block. Each time a try block does not have a catch handler for a particular exception, then the catch blocks of parent try block are inspected for that exception, if match is found that that catch block executes.

(3) IO Exception: an exception that is thrown when an I/O error occurs at compile time when reading files, directories, and streams. It is also the base class of such exceptions which occur while reading or accessing files, directories and streams. Handled by try and catch blocks.
