export interface Participants {
    errorMessage?: string;
    participants?: {
        participantId?: string;
        contactNumber?: string;
        email?: string;
        name?: {
            firstName?: string;
            middleName?: string;
            lastName?: string;
            suffix?: string;
        }
        address?: {
            addressLine1?: string;
            addressLine2?: string;
            city?: string;
            postalCode?: string;
            country?: string;
        }
    }
}