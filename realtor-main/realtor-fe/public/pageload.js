document.addEventListener('DOMContentLoaded', function() {
    const isAuthenticated = localStorage.getItem('isAuthenticated') === 'true';
    const authLinks = document.getElementById('auth-links');
    const createItemLink = document.getElementById('createPropertyLink');
    const createAgencyLink = document.getElementById('createAgencyLink');
    const savedLink = document.getElementById('savedLink');

    if (isAuthenticated) {
        authLinks.innerHTML = `
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    Profile
                </a>
                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                    <li><a class="dropdown-item" href="/profile.html">View Profile</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="#" id="logout-link">Logout</a></li>
                </ul>
            </li>
        `;
        createItemLink.style.display = 'block'; // Ensure the Create Item link is visible
    } else {
        authLinks.innerHTML = `
            <li class="nav-item">
                <a class="nav-link" href="/login.html">Login</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/register.html">Register</a>
            </li>
        `;
        createItemLink.style.display = 'none'; // Hide the Create Property link
        createAgencyLink.style.display = 'none';
        savedLink.style.display = 'none';
    }

    // Handle logout link
    document.getElementById('logout-link')?.addEventListener('click', function(ev) {
        ev.preventDefault();
        fetch('http://localhost:8080/api/account/logout', {
            credentials: 'include'
        }).then(response => {
            if (response.status === 200) {
                localStorage.removeItem('isAuthenticated');
                window.location.href = '/'; // Redirect to login page after logout
                window.history.state = null;
            }
        });
    });
});

