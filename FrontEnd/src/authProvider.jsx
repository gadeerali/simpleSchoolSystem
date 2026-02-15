const authProvider = {

    async login({username, password}) {
        const request = new Request('http://localhost:8081/auth/login', {
            method: 'POST',
            body: JSON.stringify({username, password}),
            headers: new Headers({'Content-Type': 'application/json'}),
        });
        let response;
        try {
            response = await fetch(request);
        } catch (_error) {
            throw new Error('Network Error');
        }
        if (response.status < 200 || response.status >= 300) {
            throw new Error(response.statusText);
        }
        const auth = await response.json();
        localStorage.setItem('auth', JSON.stringify(auth));
        localStorage.setItem('username', username);

    },

        async checkError(error) {
        const status = error.status;
        if (status === 401 || status === 403) {
            localStorage.removeItem('auth');
            throw new Error('Session expired');
        }
        // other error codes (404, 500, etc): no need to log out
    },
    async checkAuth() {
        if (!localStorage.getItem('auth')) {
            throw new Error('Not authenticated');
        }
    },
    async logout() {
        localStorage.removeItem('auth');
    },
    async getIdentity() {
        const username = localStorage.getItem('username');
        return { id: username, fullName: username };
    },
};

export default authProvider;