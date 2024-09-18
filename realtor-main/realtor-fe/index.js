const express = require('express');

const PORT = 3000;

const app = express();
app.use(express.static(__dirname + "/public"));

app.listen(PORT, () => console.log(`Server is listening port ${PORT}`));
