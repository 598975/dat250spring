<script>

  let newQuestion;
  let deadline;
  let newUsername;
  let newEmail;
  let options = ["", ""];

  let polls = fetch("/pollapp/polls").then((response) => {
      if (response.status === 200) {
        return response.json();
      } else {
        return "No polls found";
      }
      
    }
  )

  function addOption() {
    let removeOptionsBtn = document.getElementById('removeOptionsBtn');
    let addOptionsBtn = document.getElementById('moreOptionsBtn');

    if (options.length < 5) {
      options = [...options, ''];
    }

    if (options.length === 5) {
      addOptionsBtn.disabled = true;
    }
  
    removeOptionsBtn.disabled = false;
  }

  function removeOption() {
    let removeOptionsBtn = document.getElementById('removeOptionsBtn');
    let addOptionsBtn = document.getElementById('moreOptionsBtn');

    if (options.length > 2) {
      options = options.slice(0, -1);
      addOptionsBtn.disabled = false;
    }
    
    if (options.length === 2) {
      removeOptionsBtn.disabled = true;
    }
  }

  function register() {
    fetch("/pollapp/user", {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        username: newUsername,
        email: newEmail
      })
    }).then((response) => {
        if (response.status === 201) {
          alert('User created');
          let loginDiv = document.querySelector('.login');
          let createPollDiv = document.querySelector('.createPoll');
          let logoutDiv = document.querySelector('.logout');
          let displayPollsDiv = document.querySelector('.displayPolls');
          loginDiv.setAttribute('style', 'display: none');
          createPollDiv.setAttribute('style', 'display: flex');
          logoutDiv.setAttribute('style', 'display: flex');
          displayPollsDiv.setAttribute('style', 'display: flex');
        } else {
          alert('Something went wrong');
        }
      }).catch((error) => {
        alert(error.message);
    }) 
  }

  async function login() {
    try {
      const response = await fetch("/pollapp/user/" + newUsername, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json'
        }
      });
  
      if (response.status === 200) {
        const data = await response.json();
        if (data.email === newEmail) {
          let loginDiv = document.querySelector('.login');
          let createPollDiv = document.querySelector('.createPoll');
          let logoutDiv = document.querySelector('.logout');
          let displayPollsDiv = document.querySelector('.displayPolls');
          loginDiv.setAttribute('style', 'display: none');
          createPollDiv.setAttribute('style', 'display: flex');
          logoutDiv.setAttribute('style', 'display: flex');
          displayPollsDiv.setAttribute('style', 'display: flex');
        } else {
          alert('User not found');
        }
      } else {
        alert('User not found');
      }
    } catch (error) {
      alert(error.message);
    }
  }

  function logout() {
    newUsername = "";
    newEmail = "";
    let loginDiv = document.querySelector('.login');
    let createPollDiv = document.querySelector('.createPoll');
    let logoutDiv = document.querySelector('.logout');
    let displayPollsDiv = document.querySelector('.displayPolls');
    loginDiv.setAttribute('style', 'display: flex');
    createPollDiv.setAttribute('style', 'display: none');
    logoutDiv.setAttribute('style', 'display: none');
    displayPollsDiv.setAttribute('style', 'display: none');
  }

  function createPoll() {
    fetch("/pollapp/polls", {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        question: newQuestion,
        validUntil: new Date(deadline).toISOString(),
        options: options.map((opt, index) => {
          return {
            caption: opt,
            presentationOrder: index
          }
        }),
        pollCreator: { 
          username: newUsername,
          email: newEmail
        }
      })
    }).then((response) => {
        if (response.status === 201) {
          alert('Poll created');
          newQuestion = "";
          deadline = "";
          options = ["", ""];

          polls = fetch("/pollapp/polls").then((response) => {
            return response.json();
          })
        } else {
          alert('Something went wrong');
        }
      }).catch((error) => {
        alert(error.message);
    })
  }

  function voteOnPoll(event) {
    let pollId = event.target.id.split(',')[0];
    let optionId = event.target.id.split(',')[1];
    let optionCaption = event.target.id.split(',')[2];
    fetch("/pollapp/votes/" + newUsername, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        option : {
          presentationOrder: optionId,
          caption: optionCaption
        },
        voter : {
          username: newUsername,
          email: newEmail
        },
        pollId: pollId
      })
    }).then((response) => {
        if (response.status === 200) {
          alert('Vote registered');
          polls = fetch("/pollapp/polls").then((response) => {
            return response.json();
          })
        } else {
          alert('Something went wrong');
        }
      }).catch((error) => {
        alert(error.message);
    })
  }

</script>

<div class="login">
  <h1>Login</h1>
  <label for="username">Username</label>
  <input type="text" id="username" bind:value={newUsername} required><br>
  <label for="email">Email</label>
  <input type="email" id="email" bind:value={newEmail} required><br>
  <button on:click={login}>Login</button><button on:click={register}>Register</button>
</div>

<div class="logout">
  <h1>Logged in as {newUsername}</h1>
  <button on:click={logout}>Log out</button>
</div>

<div class="createPoll">
  <h1>Create a poll</h1>

  <label for="question">Question</label>
  <input type="text" id="question" bind:value={newQuestion} required><br>
  <label for="options">Options</label>
  <div id="options">
    {#each options as option, index}
        <input type="text" bind:value={options[index]} required><br>
    {/each}
  </div>
  <button id="moreOptionsBtn" on:click={addOption}>Add option</button><br>
  <button id="removeOptionsBtn" on:click={removeOption} disabled={true}>Remove option</button><br>
  <label for="deadline">Deadline</label>
  <input type="datetime-local" id="deadline" bind:value={deadline} required><br>
  <button on:click={createPoll}>Create</button>
  
</div>

<div class="displayPolls">
  
  {#await polls}
    loading...
  {:then ready}
    {#each Object.values(ready) as poll}
      <div>
        <h2>{poll.question}</h2>
        <ul>
          {#each poll.options as opt}
            <li>{opt.caption}, votes: {poll.votes.filter(x => x.option.presentationOrder === opt.presentationOrder).length}</li>
            <button on:click={voteOnPoll} id="{poll.id},{opt.presentationOrder},{opt.caption}">Vote</button>
          {/each}
        </ul>
        <p>Published at: {poll.publishedAt}</p>
        <p>Valid until: {poll.validUntil}</p>
      </div>
    {/each}
  {:catch error}
    {error}
  {/await}
</div>

<style>
  .createPoll, .displayPolls, .login, .logout {
    width: max-content;
    margin: 1rem auto;
    padding: 1rem;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-evenly;
    gap: 5px;
    border: 1px solid black;
    border-radius: 10px;
  }

  .createPoll, .displayPolls, .logout {
    display: none;
  }

</style>