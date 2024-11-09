class SurveyManager {
    constructor () {
        this.form = document.getElementById('beautySurvey');
        this.initializeValidation();
    }

    validateForm() {
        const required = ['skinType', 'budgetRange'];
        const checkboxGroups = ['skinConcerns', 'beautyGoals'];

        // Validate required fields
        for (let field of required) {
            if (!this.form[field].value) {
                this.showError(`Please select your ${field}`);
                return false;
            }
        }

        // Validate checkbox groups
        for (let group of checkboxGroups) {
            if (!this.form.querySelectorAll(`input[name="${group}"]:checked`).length) {
                this.showError(`Please select at least one ${group}`);
                return false;
            }
        }

        return true;
    }

    async submitSurvey(formData) {
        try {
            const response = await fetch('SaveSurvey', {
                method: 'POST',
                body: formData
            });

            const data = await response.json();

            if (data.success) {
                this.showRecommendations(data.recommendations);
                this.trackConversion();
            } else {
                this.showError(data.message);
            }
        } catch (error) {
            console.error('Error:', error);
            this.showError('An error occurred. Please try again.');
        }
    }
}

// Initialize
document.addEventListener('DOMContentLoaded', () => {
    new SurveyManager();
}); 