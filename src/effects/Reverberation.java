package effects;

public class Reverberation extends Effect {

    public Reverberation(){
       super();
    }
    public void setInputSampleStream(short[] inputAudioStream) {
        this.inputAudioStream = inputAudioStream;
    }

    @Override
    public synchronized short[] createEffect() {
        short amplitude;
        short ReverbAmplitude;
        int checkFlag;
        int delay = 5000;
        int position = 0;

        for(int i = delay ; i < this.inputAudioStream.length; i ++) {
            amplitude = this.inputAudioStream[i];
            ReverbAmplitude = this.inputAudioStream[position];
            if (i > (this.inputAudioStream.length/2))
            {
                checkFlag = ( (( ReverbAmplitude) - (int)(0.4 * amplitude)));
            }
            else
                checkFlag = ( (( ReverbAmplitude) + (int)(0.9 * amplitude)));
            if(checkFlag < Short.MAX_VALUE && checkFlag > Short.MIN_VALUE) {
                ReverbAmplitude = (short)checkFlag;
                this.inputAudioStream[position] =  ReverbAmplitude;
                position += 1;
            }
        }
        return this.inputAudioStream;
    }

    @Override
    public synchronized short[] getOutputAudioStream() {
        return this.inputAudioStream;
    }

}
