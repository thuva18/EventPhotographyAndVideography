function toggleSidebar() {
    const sidebar = document.querySelector('.sidebar');
    sidebar.classList.toggle('active');
}

// Initialize datetime picker
document.addEventListener('DOMContentLoaded', function () {
    if (typeof jQuery !== 'undefined' && typeof moment !== 'undefined' && typeof tempusDominus !== 'undefined') {
        $('#datetimePicker').datetimepicker({
            format: 'YYYY-MM-DD HH:mm:ss',
            minDate: moment(), // Disable past dates
            useCurrent: false,
            sideBySide: true
        });
    }
});