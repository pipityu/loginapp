export class Global {

    constructor() {}
    public localStorageItem(id: string): string {
        return localStorage.getItem(id);
    }
}
