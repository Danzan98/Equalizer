package effects;

public class Envelope extends Effect{

    public  Envelope()
    {
        super();
    }

    private static final double DEFAULT_ATTACK_TIME =  0.0002;//in seconds
    private static final double DEFAULT_RELEASE_TIME =  0.0004;//in seconds
    int sampleRate = 44100;
    float gainAttack;
    float gainRelease;
    float envelopeOut = 0.0f;

    public void setInputSampleStream(short[] inputAudioStream) {
        this.inputAudioStream = inputAudioStream;
    }

    @Override
    public synchronized short[] createEffect() {
        gainAttack = (float) Math.exp(-1.0/(sampleRate*DEFAULT_ATTACK_TIME));
        gainRelease = (float) Math.exp(-1.0/(sampleRate*DEFAULT_RELEASE_TIME));
        float envelopeIn;
        for (int i = 0; i < this.inputAudioStream.length; i++){
            envelopeIn = Math.abs(inputAudioStream[i]);
            if(envelopeOut < envelopeIn){
                envelopeOut =  envelopeIn + gainAttack * (envelopeOut - envelopeIn);
            }
            else {
                envelopeOut = envelopeIn + gainRelease * (envelopeOut - envelopeIn);
            }
            this.inputAudioStream[i] = (short)envelopeOut;
        }
        return this.inputAudioStream;
    }

    @Override
    public synchronized short[] getOutputAudioStream() {
        return this.inputAudioStream;
    }
}
